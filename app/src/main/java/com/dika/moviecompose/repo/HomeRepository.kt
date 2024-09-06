package com.dika.moviecompose.repo

import android.content.Context
import com.dika.moviecompose.BuildConfig
import com.dika.moviecompose.di.NetWorkResult
import com.dika.moviecompose.model.MovieRespone
import com.dika.moviecompose.model.TvShowRespone
import com.dika.moviecompose.service.RemoteDataSource
import com.dika.moviecompose.service.toResultFlow
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getMovieNowPlaying(context: Context): Flow<NetWorkResult<MovieRespone>> {
        return toResultFlow(context){
            remoteDataSource.getMovieNowPlaying(BuildConfig.API_KEY)
        }
    }


    suspend fun getTvPopular(context: Context): Flow<NetWorkResult<TvShowRespone>> {
        return toResultFlow(context){
            remoteDataSource.getTvPopular(BuildConfig.API_KEY)
        }
    }



}