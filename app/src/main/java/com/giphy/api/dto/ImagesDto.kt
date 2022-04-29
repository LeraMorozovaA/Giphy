package com.giphy.api.dto

data class ImagesDto(
    val original: OriginalImageDto
)

data class OriginalImageDto(
    val url: String
)
