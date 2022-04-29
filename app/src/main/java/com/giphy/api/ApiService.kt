package com.giphy.api

import com.giphy.api.dto.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/trending")
    suspend fun getGiphyList(@Query(value = "limit") limit: Int): GiphyResponse

    @GET("gifs/search")
    suspend fun getGiphyByQuery(
        @Query(value = "q") q: String,
        @Query(value = "limit") limit: Int
    ): GiphyResponse
}