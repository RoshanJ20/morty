package com.example.morty.domain.models

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val characters: List<Character> = emptyList()

){
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNumber"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S${seasonNumber}E${episodeNumber}"
    }
}