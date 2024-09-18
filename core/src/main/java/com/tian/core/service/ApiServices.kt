package com.tian.core.service

import com.tian.core.model.MovieRespone
import com.tian.core.model.TvShowRespone
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") api_key: String
    ): Response<MovieRespone>

    @GET("movie/popular")
    suspend fun getTvPopular(
        @Query("api_key") api_key: String
    ): Response<TvShowRespone>
}