package com.ulyanenko.memorygame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulyanenko.memorygame.data.ApiFactory
import com.ulyanenko.memorygame.data.TestResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JsonViewModel : ViewModel() {



    private val _response = MutableLiveData<TestResponse>()
    val response: LiveData<TestResponse>
        get() = _response

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiFactory.apiService.getData()
            withContext(Dispatchers.Main){
                _response.value = response
            }
        }
    }

}