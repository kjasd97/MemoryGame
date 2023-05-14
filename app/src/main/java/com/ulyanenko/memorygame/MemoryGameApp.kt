package com.ulyanenko.memorygame

import android.app.Application
import com.ulyanenko.memorygame.di.DaggerApplicationComponent

class MemoryGameApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.create()
    }

}