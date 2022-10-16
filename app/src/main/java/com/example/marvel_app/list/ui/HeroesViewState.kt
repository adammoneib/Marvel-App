package com.example.marvel_app.list.ui

import com.example.marvel_app.list.domain.Hero

sealed class HeroesViewState {
    object Loading: HeroesViewState()
    data class Success(val heroesList: List<Hero>, val totalNumber: Int): HeroesViewState()
    object Error: HeroesViewState()
}
