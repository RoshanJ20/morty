package com.example.morty.network

import com.example.morty.domain.models.Character

object MortyCache {

    val characterMap = mutableMapOf<Int, Character>()
}