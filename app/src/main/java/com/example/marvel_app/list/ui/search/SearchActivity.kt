package com.example.marvel_app.list.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ActivitySearchBinding
import com.example.marvel_app.list.ui.HeroesViewState
import com.example.marvel_app.list.ui.MainViewModel
import com.example.marvel_app.list.ui.MainViewModelProvider

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var mainViewModel: MainViewModel

    private var currentPage = 1
    private var loading: Boolean = false
    private var stopLoadingMore: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        initData()

    }

    private fun initUi() {
        searchListAdapter = SearchListAdapter()
        binding.heroesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchListAdapter
            setHasFixedSize(true)
            handleLoadMoreListener()
        }

        binding.toolbar.searchView.doOnTextChanged { text, start, before, count ->
            mainViewModel.currentList.clear()

            if(text?.isNotEmpty() == true) {
                mainViewModel.getHeroes(1, text.toString())
            }
        }

        binding.toolbar.cancelButton.setOnClickListener {
            binding.toolbar.searchView.text.clear()
        }
    }

    private fun initData() {
        mainViewModel = MainViewModelProvider.provide(this)
        mainViewModel.heroesViewState.observe(this) { state ->
            state?.let {
                setViewState(it)
            }
        }
    }

    private fun setViewState(state: HeroesViewState) {
        when (state) {
            HeroesViewState.Loading -> {}
            is HeroesViewState.Success -> {
                if(state.heroesList.isEmpty()) {
                    Toast.makeText(this, getString(R.string.empty_search), Toast.LENGTH_SHORT).show()
                }
                searchListAdapter.submitList(state.heroesList)
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
            return Intent(context, SearchActivity::class.java)
        }
    }
}