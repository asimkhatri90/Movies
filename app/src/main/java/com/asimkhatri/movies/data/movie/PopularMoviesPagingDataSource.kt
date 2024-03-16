package com.asimkhatri.movies.data.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.viewbinding.BuildConfig
import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.network.MovieService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val MOVIE_API_STARTING_PAGE_INDEX = 1

class PopularMoviesPagingDataSource @Inject constructor(
    private val movieService: MovieService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_API_STARTING_PAGE_INDEX
        return try {
            val result = movieService.getPopularMovies(position)
            val movies = result.results
            LoadResult.Page(
                movies,
                prevKey = if (position == MOVIE_API_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}