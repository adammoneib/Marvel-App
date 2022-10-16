package com.example.marvel_app.list.data

import com.example.marvel_app.list.data.models.ResponseRemote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroesRemoteDataSource {

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") query: String? = null,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<ResponseRemote>
}