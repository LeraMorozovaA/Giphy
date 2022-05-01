package com.giphy.api.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.giphy.api.ApiService
import com.giphy.api.mapper.toDataModel
import com.giphy.api.paging.MediatorHelper.Companion.LIMIT
import com.giphy.api.paging.MediatorHelper.Companion.PAGE_SIZE
import com.giphy.data.AppDatabase
import com.giphy.data.model.GiphyEntity
import com.giphy.data.model.RemoteKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class SearchGiphyMediator(
    private val query: String,
    private val apiService: ApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, GiphyEntity>() {

    private val mediatorHelper = MediatorHelper(db)

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiphyEntity>
    ): MediatorResult {
        val pageKeyData = withContext(Dispatchers.IO) { mediatorHelper.getPageData(loadType, state) }
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val list = apiService.getGiphyByQuery(q = query, limit = LIMIT, offset = page).data.map { it.toDataModel() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.giphyDao().deleteAll()
                    db.remoteKeyDao().deleteAll()
                }
                val prevKey = if (page == 0) null else page - PAGE_SIZE
                val nextKey = if (list.isEmpty()) null else page + PAGE_SIZE
                val keys = list.map {
                    RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeyDao().insertAll(keys)
                db.giphyDao().insertAll(list)
            }
            return MediatorResult.Success(endOfPaginationReached = list.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}