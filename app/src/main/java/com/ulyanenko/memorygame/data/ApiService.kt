package com.ulyanenko.memorygame.data

import retrofit2.http.GET

interface ApiService {

    @GET("json/")
    suspend fun getData ():TestResponse
}