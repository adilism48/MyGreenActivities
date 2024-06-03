package com.example.mygreenactivities.view.posts

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mygreenactivities.model.Post

class PostAdapter: ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}