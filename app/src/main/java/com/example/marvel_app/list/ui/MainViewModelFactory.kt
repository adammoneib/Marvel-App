package com.example.marvel_app.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvel_app.list.domain.GetHeroesUseCase

class MainViewModelFactory(
    private val heroesUseCase: GetHeroesUseCase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                heroesUseCase = heroesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}