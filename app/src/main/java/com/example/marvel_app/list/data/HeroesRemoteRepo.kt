package com.example.marvel_app.list.data

import com.example.marvel_app.list.domain.Hero
import com.example.marvel_app.list.domain.HeroesRepo
import io.reactivex.Single

class HeroesRemoteRepo(
    val remote: HeroesRemoteDataSource,
    val mapper: HeroesRemoteMapper
): HeroesRepo {
    override fun getHeroes(
        limit: Int,
        offset: Int,
        searchQuery: String?
    ): Single<Pair<Int,List<Hero>>> {
        return remote.getCharacters(query = searchQuery, limit = limit, offset = offset)
            .map {
                Pair(it.data.total, it.data.results.let{  list -> mapper.map(list) })
            }
    }
}