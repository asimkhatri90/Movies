package com.asimkhatri.movies.network.response

import com.asimkhatri.movies.model.Movie
import com.google.gson.annotations.SerializedName

data class CurrentPlayingMoviesResponse(
    @SerializedName("dates") val dates: Dates,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class Dates(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
)