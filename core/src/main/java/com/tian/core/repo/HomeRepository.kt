package com.tian.core.repo

import android.content.Context
import com.tian.core.BuildConfig
import com.tian.core.cons.API_KEY
import com.tian.core.di.NetWorkResult
import com.tian.core.model.MovieRespone
import com.tian.core.model.TvShowRespone
import com.tian.core.service.RemoteDataSource
import com.tian.core.service.toResultFlow
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getMovieNowPlaying(context: Context): Flow<NetWorkResult<MovieRespone>> {
        return toResultFlow(context){
            remoteDataSource.getMovieNowPlaying(API_KEY)
        }
    }


    suspend fun getTvPopular(context: Context): Flow<NetWorkResult<TvShowRespone>> {
        return toResultFlow(context){
            remoteDataSource.getTvPopular(API_KEY)
        }
    }



}