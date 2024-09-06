package com.dika.moviecompose.service

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovieNowPlaying(key: String) = apiService.getMovieNowPlaying(key)
    suspend fun getTvPopular(key: String) = apiService.getTvPopular(key)

}