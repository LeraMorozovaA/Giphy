package com.giphy.api.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.giphy.data.AppDatabase
import com.giphy.data.model.GiphyEntity
import com.giphy.data.model.RemoteKey

class MediatorHelper(private val db: AppDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPageData(loadType: LoadType, state: PagingState<Int, GiphyEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: RemoteMediator.MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GiphyEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.remoteKeyDao().remoteKeysGiphyId(id)
            }
        }
    }

    private fun getLastRemoteKey(state: PagingState<Int, GiphyEntity>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { giphy -> db.remoteKeyDao().remoteKeysGiphyId(giphy.id) }
    }

    private fun getFirstRemoteKey(state: PagingState<Int, GiphyEntity>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { giphy -> db.remoteKeyDao().remoteKeysGiphyId(giphy.id) }
    }

    companion object {
        const val LIMIT = 31
        const val PAGE_SIZE = 20
    }
}