package com.giphy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giphy.data.dao.GiphyDao
import com.giphy.data.dao.RemoteKeyDao
import com.giphy.data.model.GiphyEntity
import com.giphy.data.model.RemoteKey

@Database(entities = [GiphyEntity::class, RemoteKey::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}