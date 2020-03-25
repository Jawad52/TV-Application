package com.jawad.androidtv.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Defined all the variables as @var, because it might happen that backend send some values as
 * null
 *
 * Convert your json to kotlin data class in easy simple way, by using
 * @(JSON To Kotlin Class) plugin in android studio, you can install the plugin as any other
 * plugin and then use it, for more details check here:
 * https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-
 */

data class Player(
    @SerializedName("Id") var id: Int?, // 432380
    @SerializedName("IsCaptain") var isCaptain: Boolean?, // false
    @SerializedName("JerseyNumber") var jerseyNumber: String?, // 80
    @SerializedName("Name") var name: String?, // Abdullah Al Yousif
    @SerializedName("Order") var order: Int?, // 19
    @SerializedName("Role") var role: String?, // مدافع
    @SerializedName("StartInField") var startInField: Boolean?, // false
    @SerializedName("XCoordinate") var xCoordinate: Int?, // 0
    @SerializedName("YCoordinate") var yCoordinate: Int? // 0
)