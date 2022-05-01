package com.giphy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "giphyId")
    val giphyId: String,

    @ColumnInfo(name = "prevKey")
    val prevKey: Int?,

    @ColumnInfo(name = "nextKey")
    val nextKey: Int?
)
