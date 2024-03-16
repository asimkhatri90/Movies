package com.asimkhatri.movies.di

import androidx.viewbinding.BuildConfig
import com.asimkhatri.movies.data.movie.DefaultMovieRepository
import com.asimkhatri.movies.data.movie.MovieDataSource
import com.asimkhatri.movies.data.movie.MovieRepository
import com.asimkhatri.movies.data.movie.PopularMoviesPagingDataSource
import com.asimkhatri.movies.data.movie.RemoteMovieDataSource
import com.asimkhatri.movies.network.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        remoteMovieDataSource: RemoteMovieDataSource,
        popularMoviesPagingDataSource: PopularMoviesPagingDataSource,
        dispatcher: CoroutineDispatcher
    ): MovieRepository {
        return DefaultMovieRepository(
            remoteMovieDataSource,
            popularMoviesPagingDataSource,
            dispatcher
        )
    }
}