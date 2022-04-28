package com.giphy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giphy.data.model.GiphyEntity

@Dao
interface GiphyDao {

    @Query("SELECT * FROM giphy_items")
    fun getGiphyList(): List<GiphyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GiphyEntity>)

    @Query("DELETE FROM giphy_items")
    suspend fun deleteAll()

    @Query("DELETE FROM giphy_items WHERE id=:giphyId")
    suspend fun deleteGiphyById(giphyId: String)
}