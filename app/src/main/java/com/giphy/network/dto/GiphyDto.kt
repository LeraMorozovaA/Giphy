package com.giphy.network.dto

data class GiphyDto(
    val id: String,
    val title: String,
    val username: String,
    val images: ImagesDto
)
