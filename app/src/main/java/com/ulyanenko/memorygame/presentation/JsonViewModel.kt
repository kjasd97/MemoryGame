package com.ulyanenko.memorygame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulyanenko.memorygame.data.ApiFactory
import com.ulyanenko.memorygame.data.JsonResponse
import com.ulyanenko.memorygame.data.LoadDataRepositoryImpl
import com.ulyanenko.memorygame.domain.LoadDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JsonViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase
): ViewModel() {



    private val _response = MutableLiveData<JsonResponse>()
    val response: LiveData<JsonResponse>
        get() = _response

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = loadDataUseCase.loadData()
            withContext(Dispatchers.Main){
                _response.value = response
            }
        }
    }

}