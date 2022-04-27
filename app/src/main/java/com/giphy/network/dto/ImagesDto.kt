package com.giphy.network.dto

data class ImagesDto(
    val original: OriginalImageDto
)

data class OriginalImageDto(
    val url: String
)
