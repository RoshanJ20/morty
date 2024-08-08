package com.example.morty.episodes

import com.example.morty.domain.mappers.EpisodeMapper
import com.example.morty.domain.models.Episode
import com.example.morty.network.NetworkLayer
import com.example.morty.network.response.GetCharacterByIdResponse
import com.example.morty.network.response.GetEpisodeByIdResponse
import com.example.morty.network.response.GetEpisodePageResponse

class EpisodeRepository {

    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodePageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodePage(pageIndex)

        if( pageRequest.failed || !pageRequest.isSuccessful ) {
            return null
        }

        return pageRequest.body
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        val episodeRequest = NetworkLayer.apiClient.getEpisodeById(episodeId)

        if( episodeRequest.failed || !episodeRequest.isSuccessful ) {
            return null
        }

        val characterList = getCharactersFromEpisodeResponse(episodeRequest.body)
        return EpisodeMapper.buildFrom(
            networkEpisode = episodeRequest.body,
            networkCharacters = characterList
        )
    }

    private suspend fun getCharactersFromEpisodeResponse(episodesByIdResponse: GetEpisodeByIdResponse): List<GetCharacterByIdResponse> {
        val characterList = episodesByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf('/') + 1)
        }

        val request = NetworkLayer.apiClient.getMultipleCharacters(characterList)
        return request.body?: emptyList()
    }

}