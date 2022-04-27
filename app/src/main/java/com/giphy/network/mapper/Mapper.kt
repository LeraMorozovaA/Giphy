package com.giphy.network.mapper

import com.giphy.network.dto.GiphyDto
import com.giphy.network.dto.ImagesDto
import com.giphy.network.dto.OriginalImageDto
import com.giphy.network.model.Giphy
import com.giphy.network.model.Images
import com.giphy.network.model.OriginalImage

fun GiphyDto.toModel() = Giphy(
    id = id,
    title = title,
    username = username,
    images = images.toModel()
)

fun ImagesDto.toModel() = Images(
    original = original.toModel()
)

fun OriginalImageDto.toModel() = OriginalImage(
    url = url
)