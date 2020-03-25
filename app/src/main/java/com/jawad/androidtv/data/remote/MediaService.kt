package com.jawad.androidtv.data.remote

import com.jawad.androidtv.data.remote.dto.Data
import retrofit2.Response
import retrofit2.http.GET

/**
 * The class MediaService
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 *
 * Media REST API access points
 */
interface MediaService {
    @GET("sample.json")
    suspend fun fetchMedia(): Response<ResultsResponse<Data>>
}