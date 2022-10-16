package com.example.marvel_app.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.list.domain.GetHeroesUseCase
import com.example.marvel_app.list.domain.Hero
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val heroesUseCase: GetHeroesUseCase
): ViewModel() {

    private var currentList: MutableList<Hero> = mutableListOf()
    companion object {
        private const val PAGE_LIMIT: Int = 20
    }

    private val heroesLiveData = MutableLiveData<HeroesViewState>()
    val heroesViewState: LiveData<HeroesViewState>
        get() = heroesLiveData

    private var disposable: Disposable? = null
    fun getHeroes(
        page: Int,
        searchQuery: String?
    ) {
        heroesLiveData.value = HeroesViewState.Loading

        disposable = heroesUseCase(
            offset = (page -1) * PAGE_LIMIT,
            limit = PAGE_LIMIT,
            searchQuery = searchQuery
        ).doOnSuccess {
            currentList.addAll(it.second)
            heroesLiveData.value = HeroesViewState.Success(currentList, it.first)
        }.doOnError {
            heroesLiveData.value = HeroesViewState.Error
        }.subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}