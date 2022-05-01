package com.giphy.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.giphy.api.ApiService
import com.giphy.api.mapper.toModel
import com.giphy.api.model.Giphy
import com.giphy.api.paging.SearchGiphyMediator
import com.giphy.api.paging.TrendingGiphyMediator
import com.giphy.data.AppDatabase
import com.giphy.data.local.LocalStorageService
import com.giphy.data.model.GiphyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiphyRepository(
    private val apiService: ApiService,
    private val db: AppDatabase,
    private val localStorageService: LocalStorageService
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getGiphyList(i: Boolean?): Pager<Int, GiphyEntity>? {
        if (i == null || i == false) return null

        val pagingSourceFactory = { db.giphyDao().getAll() }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = TrendingGiphyMediator(
                apiService,
                db,
                localStorageService
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getGiphyByQuery(query: String): Pager<Int, GiphyEntity> {
        val pagingSourceFactory = { db.giphyDao().getAll() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = SearchGiphyMediator(
                query,
                apiService,
                db,
                localStorageService
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    suspend fun getGiphyListFromDb(): List<Giphy> {
        return withContext(Dispatchers.IO) {
            db.giphyDao().getGiphyList().map { it.toModel() }
        }
    }

    suspend fun removeSelectedGiphyFromDb(list: List<String>){
        withContext(Dispatchers.IO) {
            list.forEach { id ->
                db.giphyDao().deleteGiphyById(id)
                db.remoteKeyDao().deleteKeysGiphyById(id)
            }
            val newSet = localStorageService.getDeletedGiphyIdsSet().toMutableSet()
            newSet.addAll(list)
            localStorageService.setDeletedGiphyIdsSet(newSet)
        }
    }

    companion object{
        private const val PAGE_SIZE = 10
    }
}