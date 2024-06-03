package com.example.mygreenactivities.view.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mygreenactivities.databinding.PostListItemBinding
import com.example.mygreenactivities.model.Post
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostViewHolder(private val binding: PostListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            val myRef = Firebase.database.getReference("users")

            myRef.child(post.userUID ?: "Error no UID").get().addOnSuccessListener {
                tvEmail.text = it.child("email").value.toString()
                tvEmail.isVisible = it.child("showEmail").value as Boolean
                tvName.text = it.child("name").value.toString()
            }

            tvPost.text = post.text
            tvTag.text = post.tag
        }
    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            return PostViewHolder(
                PostListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}