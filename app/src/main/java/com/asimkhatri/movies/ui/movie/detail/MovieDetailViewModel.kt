package com.asimkhatri.movies.ui.movie.detail

import androidx.lifecycle.*
import com.asimkhatri.movies.data.movie.MovieRepository
import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _movieId: MutableStateFlow<String> = MutableStateFlow("")

    private val _movie: StateFlow<Result<Movie>> = _movieId.transform {
        if (it != "") emitAll(movieRepository.getMovie(it))
    }.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = Result.Loading)

    val movie get() = _movie

    fun getMovieDetails(movieId: String) {
        _movieId.value = movieId
    }
}