package com.example.marvel_app.list.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.marvel_app.list.domain.Hero

object HeroesDiffUtil: DiffUtil.ItemCallback<Hero>() {
    override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.thumbnailUrl == newItem.thumbnailUrl
    }
}