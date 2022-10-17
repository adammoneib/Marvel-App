package com.example.marvel_app.list.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel_app.R
import com.example.marvel_app.databinding.SearchListItemBinding
import com.example.marvel_app.list.domain.Hero

class SearchListViewHolder(private val binding: SearchListItemBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bindItem(hero: Hero) {
            Glide.with(itemView.context)
                .load(hero.thumbnailUrl)
                .placeholder(R.drawable.image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.heroPhoto)
            binding.heroName.text = hero.name
        }
}