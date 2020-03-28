package com.jawad.androidtv.data

import androidx.lifecycle.MutableLiveData
import com.jawad.androidtv.data.remote.dataSource.RemoteDataSource
import com.jawad.androidtv.data.remote.dto.Data
import com.jawad.androidtv.data.remote.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The class DataRepository
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 *
 * Data Repository module for handling data operations.
 */

@Singleton
class DataRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    /**
     * API response handled in this function
     * returned Data from the server
     * @param scope to preform operation in background thread
     */
    fun getLineupsData(scope: CoroutineScope): MutableLiveData<Result<Data>> {
        val result = MutableLiveData<Result<Data>>()
        result.value = Result.loading()
        scope.launch(Dispatchers.IO) {
            val response = remoteDataSource.fetchData()
            try {
                withContext(Dispatchers.Main) {
                    if (response.status == Result.Status.SUCCESS) {
                        val postListResponse = response.data!!.lineups
                        result.value = Result.success(postListResponse.data)
                    } else if (response.status == Result.Status.ERROR) {
                        result.value = Result.error(response.message!!)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    result.value = Result.error("Unable to get data")
                }
            }
        }
        return result
    }
}