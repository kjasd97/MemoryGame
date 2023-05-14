package com.ulyanenko.memorygame.domain

import com.ulyanenko.memorygame.data.JsonResponse
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: LoadDataRepository) {

   suspend fun loadData():JsonResponse{
       return repository.loadData()
   }

}