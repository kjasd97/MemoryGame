package com.ulyanenko.memorygame.domain

import com.ulyanenko.memorygame.data.JsonResponse

interface LoadDataRepository {

    suspend fun loadData(): JsonResponse

}