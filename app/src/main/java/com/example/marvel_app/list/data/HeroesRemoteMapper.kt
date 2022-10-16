package com.example.marvel_app.list.data

import com.example.marvel_app.list.data.models.HeroRemote
import com.example.marvel_app.list.domain.Hero

object HeroesRemoteMapper {
    private fun map(remote: HeroRemote): Hero {
        return Hero(
            id= remote.id,
            name = remote.name,
            thumbnailUrl = remote.thumbnail?.path + "." + remote.thumbnail?.extension
        )
    }

    fun map(list: List<HeroRemote>): List<Hero> {
        return list.map { map(it) }
    }
 }