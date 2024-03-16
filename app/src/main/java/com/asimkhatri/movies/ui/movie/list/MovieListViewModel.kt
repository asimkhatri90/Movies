package com.asimkhatri.movies.ui.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.asimkhatri.movies.data.movie.MovieRepository
import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _currentPlayingMovies: StateFlow<Result<List<Movie>>> =
        movieRepository.getCurrentPlayingMovies(1).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Result.Loading
        )

    val currentPlayingMovies get() = _currentPlayingMovies

    private val _popularMovies = movieRepository.getPopularMovies().cachedIn(viewModelScope)

    val popularMovies get() = _popularMovies

}