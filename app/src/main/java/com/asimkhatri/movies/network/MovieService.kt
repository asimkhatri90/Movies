package com.asimkhatri.movies.network

import com.asimkhatri.movies.model.Movie
import com.asimkhatri.movies.network.response.CurrentPlayingMoviesResponse
import com.asimkhatri.movies.network.response.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The Retrofit service class to fetch movie related data from the Movie Db API
 */

interface MovieService {
    @GET("movie/now_playing?language=en-US")
    suspend fun getCurrentPlayingMovies(
        @Query("page") page: Int
    ): CurrentPlayingMoviesResponse

    @GET("movie/{id}?language=en-US")
    suspend fun getMovie(@Path("id") id: String): Movie

    @GET("movie/popular?language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): PopularMoviesResponse
}