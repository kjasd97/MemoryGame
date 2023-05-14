package com.ulyanenko.memorygame.di

import com.ulyanenko.memorygame.presentation.GameActivity
import com.ulyanenko.memorygame.presentation.JsonActivity
import dagger.Component

@Component(modules = [ViewModelModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(activity:GameActivity)

    fun inject(activity: JsonActivity)

}