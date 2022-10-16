package com.example.marvel_app

import android.app.Application

class MarvelApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DefaultApiClient.init()
    }
}