package com.ulyanenko.memorygame.di

import com.ulyanenko.memorygame.data.LoadDataRepositoryImpl
import com.ulyanenko.memorygame.domain.LoadDataRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindLoadDataRepository(impl: LoadDataRepositoryImpl):LoadDataRepository

}