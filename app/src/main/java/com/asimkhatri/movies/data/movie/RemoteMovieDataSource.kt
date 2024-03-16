package com.asimkhatri.movies.data.movie

import androidx.viewbinding.BuildConfig
import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.network.MovieService
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getCurrentPlayingMovies(page: Int): List<Movie>

    suspend fun getMovie(id: String): Movie
}

class RemoteMovieDataSource @Inject constructor(
    private val movieService: MovieService
) : MovieDataSource {
    override suspend fun getCurrentPlayingMovies(page: Int): List<Movie> {
        val response = movieService.getCurrentPlayingMovies(page)
        return response.results
    }

    override suspend fun getMovie(id: String) = movieService.getMovie(id)
}