package com.asimkhatri.movies.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asimkhatri.movies.databinding.MovieItemBinding
import com.asimkhatri.movies.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PopularMoviesPagerAdapter(private val listener: OnMovieClickListener) :
    PagingDataAdapter<Movie, PopularMoviesPagerAdapter.PopularMoviesViewHolder>(
        PopularMoviesDiffUtil
    ) {

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        return PopularMoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PopularMoviesViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
            binding.apply {
                title.text = item.title
                releaseDate.text = item.release_date
                val imageUrl = "https://image.tmdb.org/t/p/original/${item.poster_path}"
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.poster)
            }
        }
    }

    object PopularMoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}