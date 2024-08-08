package com.example.morty.network.response

data class GetEpisodePageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)
