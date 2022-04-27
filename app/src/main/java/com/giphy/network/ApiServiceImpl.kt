package com.giphy.network

import com.giphy.network.dto.GiphyDto
import com.giphy.network.dto.GiphyResponse
import com.giphy.network.dto.ImagesDto
import com.giphy.network.dto.OriginalImageDto
import retrofit2.http.Query

class ApiServiceImpl: ApiService {

    override suspend fun getGiphyList(@Query(value = "limit") limit: Int): GiphyResponse {
        return GiphyResponse(listOf(
            GiphyDto("rVzvUgOpJlQkS06ZMO","Nba Playoffs Sport GIF","", ImagesDto(OriginalImageDto("https://media3.giphy.com/media/JqYw4E5THqXeBaUevp/giphy.gif?cid=af679f2e2wh45g0be1dcoa3egy8e37x9gsih8olb6gz72de6&rid=giphy.gif&ct=g"))),
            GiphyDto("u6FQFr9lSbjPwWbLjd","Celebrate Happy Birthday GIF by The Tonight Show Starring Jimmy Fallon","fallontonight", ImagesDto(OriginalImageDto("https://media4.giphy.com/media/u6FQFr9lSbjPwWbLjd/giphy.gif?cid=af679f2e6l01570y72230ucaiq65idhpsu2lyaat7oea0vsa&rid=giphy.gif&ct=g"))))
        )
    }
}