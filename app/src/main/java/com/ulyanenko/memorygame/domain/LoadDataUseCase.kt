package com.ulyanenko.memorygame.domain

import com.ulyanenko.memorygame.data.JsonResponse

class LoadDataUseCase(private val repository: LoadDataRepository) {

   suspend fun loadData():JsonResponse{
       return repository.loadData()
   }

}