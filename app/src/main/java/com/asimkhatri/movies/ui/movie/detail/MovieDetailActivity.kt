package com.asimkhatri.movies.ui.movie.detail

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.asimkhatri.movies.databinding.ActivityMovieDetailBinding
import com.asimkhatri.movies.result.Result
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        initFlow()
        val movieId = intent.getStringExtra("MovieId") ?: ""
        viewModel.getMovieDetails(movieId)
    }

    private fun initFlow() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collect { result ->
                    // New value received
                    when (result) {
                        is Result.Loading -> {
                            Timber.i("Loading")
                        }
                        is Result.Success -> {
                            Timber.i("Success %s", result.data)
                            val movie = result.data
                            binding.apply {
                                title.text = movie.title
                                duration.text = movie.runtime.toString()
                                releaseDate.text = movie.release_date
                                overview.text = movie.overview
                                val imageUrl =
                                    "https://image.tmdb.org/t/p/original/${movie.poster_path}"
                                Glide.with(root.context)
                                    .load(imageUrl)
                                    .fitCenter()
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(poster)
                            }
                        }
                        is Result.Error -> {
                            Timber.e(result.exception)
                            showErrorDialog(
                                result.exception.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}