package com.example.marvel_app.list.data.models

data class DataRemote(
    val offset: Int,
    val total: Int,
    val results: List<HeroRemote>
)
