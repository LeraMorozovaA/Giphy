package com.giphy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giphy.data.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE giphyId=:id")
    fun remoteKeysGiphyId(id: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

    @Query("DELETE FROM remote_keys WHERE giphyId=:id")
    fun deleteKeysGiphyById(id: String)
}