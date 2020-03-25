package com.jawad.androidtv.data.remote.dataSource

import com.jawad.androidtv.data.remote.BaseDataSource
import com.jawad.androidtv.data.remote.MediaService
import javax.inject.Inject

/**
 * The class RemoteDataSource
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 *
 * Works with the News API to get all the data from server.
 */
class RemoteDataSource @Inject constructor(private val newsService: MediaService) :
    BaseDataSource() {
    suspend fun fetchNews() = getResult { newsService.fetchMedia() }
}
