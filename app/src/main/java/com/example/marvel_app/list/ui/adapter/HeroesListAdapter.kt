package com.example.marvel_app.list.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ListItemBinding
import com.example.marvel_app.databinding.LoadingProgressItemBinding
import com.example.marvel_app.list.domain.Hero

class HeroesListAdapter(
    private val heroClicked : (Hero) -> Unit
): ListAdapter<Hero, RecyclerView.ViewHolder>(HeroesDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ViewType.VIEW_ITEM.ordinal) {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            ListItemViewHolder(ListItemBinding.bind(itemView))
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_progress_item, parent, false)
            ProgressViewHolder(LoadingProgressItemBinding.bind(view))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListItemViewHolder).bindItem(getItem(position), heroClicked)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) != null)
            ViewType.VIEW_ITEM.ordinal
        else
            ViewType.VIEW_PROGRESS.ordinal
    }

    enum class ViewType {
        VIEW_ITEM,
        VIEW_PROGRESS
    }
}