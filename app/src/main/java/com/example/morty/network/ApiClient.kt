package com.example.morty.network

import com.example.morty.network.response.GetCharacterByIdResponse
import com.example.morty.network.response.GetCharactersPageResponse
import com.example.morty.network.response.GetEpisodeByIdResponse
import com.example.morty.network.response.GetEpisodePageResponse
import retrofit2.Response

class ApiClient (
    private val rickAndMortyService: RickAndMortyService
){
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(pageIndex) }
    }

    suspend fun getCharactersPage(characterName: String ,pageIndex: Int ): SimpleResponse<GetCharactersPageResponse>{
        return safeApiCall { rickAndMortyService.getCharactersPageByName(characterName, pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodePage(pageIndex: Int): SimpleResponse<GetEpisodePageResponse> {
        return safeApiCall { rickAndMortyService.getEpisodePage(pageIndex) }
    }
    suspend fun getMultipleCharacters(characterList: List<String>): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { rickAndMortyService.getMultipleCharacters(characterList) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }
}