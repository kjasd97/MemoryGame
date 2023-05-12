package com.ulyanenko.memorygame.data

import com.google.gson.annotations.SerializedName

data class TestResponse (
    @SerializedName("country") val country:String,
    @SerializedName("city") val city:String,
    @SerializedName("timezone") val timezone:String
) {
}