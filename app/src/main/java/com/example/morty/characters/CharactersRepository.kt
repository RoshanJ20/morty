package com.example.morty.characters

import com.example.morty.network.NetworkLayer
import com.example.morty.network.response.GetCharactersPageResponse

class CharactersRepository {

    suspend fun getCharacterByList(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)
        if(request.failed || !request.isSuccessful) {
            return null
        }
        return request.body
    }



}
