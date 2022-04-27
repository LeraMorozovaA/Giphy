package com.giphy.network

import com.giphy.network.dto.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/trending")
    suspend fun getGiphyList(@Query(value = "limit") limit: Int): GiphyResponse

}