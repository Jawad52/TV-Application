package com.jawad.androidtv.data.remote

import retrofit2.Response

/**
 * The class BaseDataSource
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 *
 * Base Data source class with error handling
 */

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error("No Internet Connection")
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error(message)
    }
}