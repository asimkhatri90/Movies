package com.asimkhatri.movies.ui.movie.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.asimkhatri.movies.R
import com.asimkhatri.movies.databinding.ActivityMainBinding
import com.asimkhatri.movies.result.Result
import com.asimkhatri.movies.ui.movie.detail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMovieClickListener {

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initFlow()
    }

    private fun initViews() {
        moviesAdapter = MoviesAdapter(this)
        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        popularMoviesAdapter = PopularMoviesPagerAdapter(this)
        val dividerItemDecoration =
            DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        ContextCompat.getDrawable(applicationContext, R.drawable.divider)
            ?.let { dividerItemDecoration.setDrawable(it) }
        binding.recyclerView02.addItemDecoration(dividerItemDecoration)
        binding.recyclerView02.adapter = popularMoviesAdapter
        binding.recyclerView02.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initFlow() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentPlayingMovies.collect { result ->
                    // New value received
                    when (result) {
                        is Result.Loading -> {
                            Timber.i("Loading")

                        }
                        is Result.Success -> {
                            Timber.i("Success %s", result.data)
                            moviesAdapter.submitList(result.data)
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularMovies.collect {
                    popularMoviesAdapter.submitData(it)
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

    override fun onMovieClick(movieId: String) {
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        intent.putExtra("MovieId", movieId)
        startActivity(intent)
    }
}
