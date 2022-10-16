package com.example.marvel_app.list.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.marvel_app.list.domain.GetHeroesUseCaseProvider

object MainViewModelProvider {

    private fun provideGetHeroesUseCase() = GetHeroesUseCaseProvider.provide()

    fun provide(owner: AppCompatActivity) = ViewModelProvider(
        owner = owner,
        MainViewModelFactory(
            heroesUseCase = provideGetHeroesUseCase()
        )
    ).get(MainViewModel::class.java)
}