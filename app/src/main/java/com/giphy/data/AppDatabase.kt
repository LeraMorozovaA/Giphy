package com.giphy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giphy.data.dao.GiphyDao
import com.giphy.data.model.GiphyEntity

@Database(entities = [GiphyEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
}