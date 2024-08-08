package com.example.morty

import android.app.Application
import android.content.Context

class MortyApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}