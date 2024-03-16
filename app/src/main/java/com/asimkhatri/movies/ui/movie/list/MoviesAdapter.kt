package com.asimkhatri.movies.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asimkhatri.movies.databinding.CurrentPlayingMovieItemBinding
import com.asimkhatri.movies.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MoviesAdapter(private val listener: OnMovieClickListener) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            CurrentPlayingMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        (holder as MovieViewHolder).bind(movie)
    }

    inner class MovieViewHolder(
        private val binding: CurrentPlayingMovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onMovieClick(item.id.toString())
                    }
                }
            }
        }

        fun bind(item: Movie) {
            val imageUrl = "https://image.tmdb.org/t/p/original/${item.poster_path}"
            Glide.with(binding.root.context)
                .load(imageUrl)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.poster)
        }
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}