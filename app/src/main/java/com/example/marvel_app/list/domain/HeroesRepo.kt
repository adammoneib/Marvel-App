package com.example.marvel_app.list.domain

import io.reactivex.Single

interface HeroesRepo {

    fun getHeroes(
        limit: Int,
        offset: Int,
        searchQuery: String? = null
    ): Single<Pair<Int,List<Hero>>>
}