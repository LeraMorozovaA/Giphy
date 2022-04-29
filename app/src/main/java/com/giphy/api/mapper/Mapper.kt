package com.giphy.api.mapper

import com.giphy.data.model.GiphyEntity
import com.giphy.api.dto.GiphyDto
import com.giphy.api.dto.ImagesDto
import com.giphy.api.dto.OriginalImageDto
import com.giphy.api.model.Giphy
import com.giphy.api.model.Images
import com.giphy.api.model.OriginalImage

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