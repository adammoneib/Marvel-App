package com.example.marvel_app.list.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.databinding.SearchListItemBinding
import com.example.marvel_app.list.domain.Hero
import com.example.marvel_app.list.ui.adapter.HeroesDiffUtil

class SearchListAdapter: ListAdapter<Hero, RecyclerView.ViewHolder>(HeroesDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.search_list_item, parent, false)
        return SearchListViewHolder(SearchListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchListViewHolder).bindItem(getItem(position))
    }
}