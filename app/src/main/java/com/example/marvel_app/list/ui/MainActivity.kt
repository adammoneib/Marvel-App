package com.example.marvel_app.list.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.databinding.ActivityMainBinding
import com.example.marvel_app.list.domain.Hero
import com.example.marvel_app.list.ui.adapter.HeroesListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: HeroesListAdapter
    private lateinit var mainViewModel: MainViewModel

    private var currentPage = 1
    private var loading: Boolean = false
    private var stopLoadingMore: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initData()
    }

    private fun initUI() {
        listAdapter = HeroesListAdapter { openDetails(it) }
        binding.heroesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
            setHasFixedSize(true)
            handleLoadMoreListener()
        }
        binding.toolbar.searchIcon.setOnClickListener{
            openSearchActivity()
        }
    }

    private fun initData() {
        mainViewModel = MainViewModelProvider.provide(this)
        mainViewModel.heroesViewState.observe(this) { state ->
            state?.let {
                setViewState(it)
            }
        }
        mainViewModel.getHeroes(currentPage, null)
    }

    private fun setViewState(state: HeroesViewState) {
        when (state) {
            HeroesViewState.Loading -> {}
            is HeroesViewState.Success -> {
                listAdapter.submitList(state.heroesList)
                if (state.heroesList.size >= state.totalNumber) {
                    if (currentPage > 0) {
                        currentPage -= 1
                    }
                    stopLoadingMore = true
                }
                loading = false
                loading = false
            }
            HeroesViewState.Error -> {}
        }
    }

    private fun openDetails(hero: Hero) {

    }

    private fun openSearchActivity(){

    }
    private fun handleLoadMoreListener() {
        binding.heroesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)

                val VISIBLE_THRESHOLD = 1
                var totalItemCount = 0
                var lastVisibleItem = 0

                recyclerView.layoutManager?.let {
                    totalItemCount = it.itemCount
                    lastVisibleItem =
                        (it as LinearLayoutManager).findLastVisibleItemPosition()
                }

                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    if (!stopLoadingMore) {
                        currentPage++
                        mainViewModel.getHeroes(currentPage, null)
                    }
                    loading = true
                }
            }
        })
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}