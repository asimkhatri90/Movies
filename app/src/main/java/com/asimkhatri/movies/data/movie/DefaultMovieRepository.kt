package com.asimkhatri.movies.data.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieRepository {
    fun getCurrentPlayingMovies(page: Int): Flow<Result<List<Movie>>>

    fun getMovie(id: String): Flow<Result<Movie>>

    fun getPopularMovies(): Flow<PagingData<Movie>>
}

class DefaultMovieRepository @Inject constructor(
    private val dataSource: MovieDataSource,
    private val pagingDataSource: PagingSource<Int, Movie>,
    private val coroutineDispatcher: CoroutineDispatcher
) : MovieRepository {
    override fun getCurrentPlayingMovies(page: Int): Flow<Result<List<Movie>>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(dataSource.getCurrentPlayingMovies(page)))
        }.catch { e -> emit(Result.Error(Exception(e))) }
            .flowOn(coroutineDispatcher)
    }

    override fun getMovie(id: String): Flow<Result<Movie>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(dataSource.getMovie(id)))
        }.catch { e -> emit(Result.Error(Exception(e))) }
            .flowOn(coroutineDispatcher)
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false, maxSize = 60),
            pagingSourceFactory = { pagingDataSource }
        ).flow
    }
}