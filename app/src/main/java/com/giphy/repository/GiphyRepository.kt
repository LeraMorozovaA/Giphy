package com.giphy.repository

import com.giphy.network.ApiService
import com.giphy.network.mapper.toModel
import com.giphy.network.model.Giphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyRepository(private val apiService: ApiService) {

    suspend fun getGiphyList(): List<Giphy> {
        return withContext(Dispatchers.IO) {
            apiService.getGiphyList(2).data.map { it.toModel() }
        }
    }
}