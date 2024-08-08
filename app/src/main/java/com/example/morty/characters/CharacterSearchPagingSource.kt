package com.example.morty.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.morty.domain.mappers.CharacterMapper
import com.example.morty.domain.models.Character
import com.example.morty.network.NetworkLayer

class CharacterSearchPagingSource(
    private val userSearch: String,
    private val localExceptionCallback: (LocalException) -> Unit
) : PagingSource<Int, Character>() {

    sealed class LocalException(
        val title : String,
        val description: String = ""
    ) : Exception() {
        data object EmptySearch : LocalException(
            title = "Empty Search",
            description = "Please enter a search term"
        ) {
            private fun readResolve(): Any = EmptySearch
        }

        data object NoResults : LocalException(
            title = "No Results",
            description = "Looks like your search didn't return any results"
        ) {
            private fun readResolve(): Any = NoResults
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        if(userSearch.isEmpty()){
            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1

        val request = NetworkLayer.apiClient.getCharactersPage(
            characterName = userSearch,
            pageIndex = pageNumber
        )

        if(request.data?.code() == 404){
            val exception = LocalException.NoResults
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        request.exception?.let{
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = request.bodyNullable?.results?.map{
                CharacterMapper.buildFrom(it)
            } ?: emptyList(),
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(request.bodyNullable?.info?.next)
        )

    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        if(next == null) return null

        val remainder = next.substringAfter("?page=")
        val finalIndex = if(remainder.contains("&")) remainder.indexOfFirst { it == '&' } else remainder.length
        return remainder.substring(0, finalIndex).toIntOrNull()
    }
}