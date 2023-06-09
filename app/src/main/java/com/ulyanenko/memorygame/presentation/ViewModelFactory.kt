package com.ulyanenko.memorygame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory@Inject constructor(
private val viewModelProviders:  @JvmSuppressWildcards Map<String, Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass.simpleName]?.get() as T
    }
}