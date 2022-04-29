package com.giphy.api.model

data class Images(
    val original: OriginalImage
)

data class OriginalImage(
    val url: String
)
