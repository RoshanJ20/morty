package com.example.morty.characters

import com.example.morty.domain.mappers.CharacterMapper
import com.example.morty.domain.models.Character
import com.example.morty.network.MortyCache
import com.example.morty.network.NetworkLayer
import com.example.morty.network.response.GetCharacterByIdResponse
import com.example.morty.network.response.GetEpisodeByIdResponse

class CharacterRepository {

    suspend fun getCharacterById(characterId: Int): Character? {
        val cachedCharacter = MortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            return cachedCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed  || !request.isSuccessful) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )

        MortyCache.characterMap[characterId] = character
        return character
    }


    private suspend fun getEpisodesFromCharacterResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map{
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()
        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if(request.failed || !request.isSuccessful){
            return emptyList()
        }

        return request.body
    }
}