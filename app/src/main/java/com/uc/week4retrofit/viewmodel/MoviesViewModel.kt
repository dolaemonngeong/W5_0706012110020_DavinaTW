package com.uc.week4retrofit.viewmodel

import com.uc.week4retrofit.model.Result
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uc.week4retrofit.model.MovieDetails
import com.uc.week4retrofit.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MoviesViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel(){

    //Get Now Playing Data
    val _nowPlaying: MutableLiveData<ArrayList<Result>> by lazy {
        MutableLiveData<ArrayList<Result>>()
    }

    val nowPlaying: LiveData<ArrayList<Result>>
        get() = _nowPlaying

    fun getNowPlaying(apiKey: String, language: String, page: Int) = viewModelScope.launch {
        repository.getNowPlayingData(apiKey, language, page).let {
                response ->
            if (response.isSuccessful){
                _nowPlaying.postValue(response.body()?.results as
                        ArrayList<Result>?)
            } else{
                Log.e("Get Data", "Failed!")
            }
        }
    }

    //Get Movie Details
    val _movieDetails: MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }

    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    fun getMovieDetail(id:Int, apiKey: String) = viewModelScope.launch {
        repository.getMovieDetailResults(id, apiKey).let {
                response ->
            if (response.isSuccessful){
                _movieDetails.postValue(response.body() as MovieDetails)
            } else{
                Log.e("Get Data", "Failed!")
            }
        }
    }
}