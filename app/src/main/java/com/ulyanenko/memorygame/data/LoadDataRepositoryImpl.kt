package com.ulyanenko.memorygame.data

import com.ulyanenko.memorygame.domain.LoadDataRepository
import javax.inject.Inject

class LoadDataRepositoryImpl @Inject constructor(): LoadDataRepository {

    override suspend fun loadData(): JsonResponse {
      return  ApiFactory.apiService.getData()
    }

}