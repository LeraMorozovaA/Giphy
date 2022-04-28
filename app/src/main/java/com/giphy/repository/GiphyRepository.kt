package com.giphy.repository

import com.giphy.network.ApiService
import com.giphy.network.mapper.toModel
import com.giphy.network.model.Giphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyRepository(private val apiService: ApiService) {

    private val limit = 2

    suspend fun getGiphyList(): List<Giphy> {
        return withContext(Dispatchers.IO) {
            apiService.getGiphyList(limit).data.map { it.toModel() }
        }
    }

    suspend fun getGiphyByQuery(query: String): List<Giphy> {
        return withContext(Dispatchers.IO) {
            apiService.getGiphyByQuery(query, limit).data.map { it.toModel() }
        }
    }
}