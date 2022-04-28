package com.giphy.network.mapper

import com.giphy.data.model.GiphyEntity
import com.giphy.network.dto.GiphyDto
import com.giphy.network.dto.ImagesDto
import com.giphy.network.dto.OriginalImageDto
import com.giphy.network.model.Giphy
import com.giphy.network.model.Images
import com.giphy.network.model.OriginalImage

fun GiphyDto.toDataModel() = GiphyEntity(
    id = id,
    title = title,
    username = username,
    url = images.original.url
)

fun ImagesDto.toModel() = Images(
    original = original.toModel()
)

fun OriginalImageDto.toModel() = OriginalImage(
    url = url
)

fun GiphyEntity.toModel() = Giphy(
    id = id,
    title = title,
    username = username,
    url = url
)