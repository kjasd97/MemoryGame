package com.ulyanenko.memorygame.di

import androidx.lifecycle.ViewModel
import com.ulyanenko.memorygame.presentation.GameViewModel
import com.ulyanenko.memorygame.presentation.JsonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @Binds
    @StringKey("GameViewModel")
    @IntoMap
    fun bindGameViewModel(viewModel:GameViewModel):ViewModel

    @Binds
    @StringKey("JsonViewModel")
    @IntoMap
    fun bindJsonViewModel(viewModel:JsonViewModel):ViewModel


}