package com.giphy.api.model

data class Giphy(
    val id: String,
    val title: String,
    val username: String,
    val url: String,
    var isSelected: Boolean = false
)
