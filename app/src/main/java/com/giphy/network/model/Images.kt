package com.giphy.network.model

data class Images(
    val original: OriginalImage
)

data class OriginalImage(
    val url: String
)
