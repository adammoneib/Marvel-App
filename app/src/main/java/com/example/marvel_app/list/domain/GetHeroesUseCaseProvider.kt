package com.example.marvel_app.list.domain

import com.example.marvel_app.DefaultApiClient
import com.example.marvel_app.list.data.HeroesRemoteDataSource
import com.example.marvel_app.list.data.HeroesRemoteMapper
import com.example.marvel_app.list.data.HeroesRemoteRepo

object GetHeroesUseCaseProvider {

    private fun provideRemoteRepo() = HeroesRemoteRepo(mapper = provideMapper(), remote = provideRemoteDataSource())

    private fun provideMapper() = HeroesRemoteMapper

    private fun provideRemoteDataSource(): HeroesRemoteDataSource = DefaultApiClient.getInstance().create(HeroesRemoteDataSource::class.java)

    fun provide(): GetHeroesUseCase {
        return GetHeroesUseCase(provideRemoteRepo())
    }
}