package com.ulyanenko.memorygame.data

import com.ulyanenko.memorygame.domain.LoadDataRepository

class LoadDataRepositoryImpl: LoadDataRepository {

    override suspend fun loadData(): JsonResponse {
      return  ApiFactory.apiService.getData()
    }

}