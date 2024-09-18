package com.dika.moviecompose.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tian.core.base.BaseViewModel

import com.tian.core.di.NetWorkResult
import com.tian.core.model.MovieRespone
import com.tian.core.model.TvShowRespone
import com.tian.core.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository,
                                        application: Application):BaseViewModel(application) {

    private val _response: MutableLiveData<  NetWorkResult<MovieRespone>> = MutableLiveData()
    private val _responseTV: MutableLiveData<  NetWorkResult<TvShowRespone>> = MutableLiveData()

    val response: LiveData<NetWorkResult<MovieRespone>> = _response
    val responseTvPopular: LiveData<NetWorkResult<TvShowRespone>> = _responseTV



    fun getMovieNowPlaying() = viewModelScope.launch {
        repository.getMovieNowPlaying(context).collect { values ->
            _response.value = values
        }
    }

    fun getTvPopular() = viewModelScope.launch {
        repository.getTvPopular(context).collect { values ->
            _responseTV.value = values
        }
    }

}