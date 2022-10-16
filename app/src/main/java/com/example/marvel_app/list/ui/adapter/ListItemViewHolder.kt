package com.example.marvel_app.list.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ListItemBinding
import com.example.marvel_app.list.domain.Hero

class ListItemViewHolder(private val binding: ListItemBinding):
 RecyclerView.ViewHolder(binding.root){

     fun bindItem(hero: Hero, heroClicked: ((Hero) -> Unit)) {

         binding.root.setOnClickListener { heroClicked(hero) }

         binding.heroName.text = hero.name
         Glide.with(itemView.context)
             .load(hero.thumbnailUrl)
             .placeholder(R.drawable.image_placeholder)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .into(binding.heroIv)
     }
}