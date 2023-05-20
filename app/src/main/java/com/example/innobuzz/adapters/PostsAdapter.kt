package com.example.innobuzz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzz.databinding.LayoutItemPostBinding
import com.example.innobuzz.db.entity.Posts

class PostsAdapter(private val postsList: List<Posts>, private val listener: PostClickListener) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutItemPostBinding = LayoutItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postsList[position])
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    inner class ViewHolder(private val binding: LayoutItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onPostClicked(postsList[adapterPosition])
            }
        }

        fun bind(post: Posts?) {
            if (post != null) {
                binding.postTitleTxt.text = post.title
            }
        }
    }
}

interface PostClickListener {
    fun onPostClicked(posts: Posts)
}