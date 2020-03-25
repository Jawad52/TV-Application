package com.jawad.androidtv.data.remote

import com.google.gson.annotations.SerializedName

/**
 * The class ResultsResponse
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 25 Mar 2020
 */

data class ResultsResponse<T>(
    @SerializedName("Lineups")
    val lineups: Lineups<T>
)

data class Lineups<T>(
    @SerializedName("Success")
    val success: Int,
    @SerializedName("Data")
    val data: List<T>
)