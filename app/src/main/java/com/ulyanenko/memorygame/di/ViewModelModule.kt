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
    @ViewModelKey(GameViewModel::class)
    @IntoMap
    fun bindGameViewModel(viewModel:GameViewModel):ViewModel

    @Binds
    @ViewModelKey(JsonViewModel::class)
    @IntoMap
    fun bindJsonViewModel(viewModel:JsonViewModel):ViewModel


}