package com.dika.moviecompose.model

data class MovieRespone(
    val status_message : String,
    val results: List<Movie>
)

data class Movie(
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val title: String,
    val release_date: String,
    val original_language: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Float
)

