package com.example.marvel_app.list.domain

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetHeroesUseCase(
    val repo: HeroesRepo
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        searchQuery: String?): Single<Pair<Int, List<Hero>>> {
        return repo.getHeroes(limit = limit, offset = offset, searchQuery = searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}