package com.giphy.repository

import com.giphy.data.dao.GiphyDao
import com.giphy.api.ApiService
import com.giphy.api.mapper.toDataModel
import com.giphy.api.mapper.toModel
import com.giphy.api.model.Giphy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyRepository(
    private val apiService: ApiService,
    private val dao: GiphyDao
) {

    private val limit = 15

    suspend fun getGiphyList(): List<Giphy> {
        return withContext(Dispatchers.IO) {
            val list = apiService.getGiphyList(limit).data.map { it.toDataModel() }
            dao.deleteAll()
            dao.insertAll(list)
            list.map { it.toModel() }
        }
    }

    suspend fun getGiphyListFromDb(): List<Giphy> {
        return withContext(Dispatchers.IO) {
            dao.getGiphyList().map { it.toModel() }
        }
    }

    suspend fun getGiphyByQuery(query: String): List<Giphy> {
        return withContext(Dispatchers.IO) {
            val list = apiService.getGiphyByQuery(query, 3).data.map { it.toDataModel() }
            dao.deleteAll()
            dao.insertAll(list)
            list.map { it.toModel() }
        }
    }
}