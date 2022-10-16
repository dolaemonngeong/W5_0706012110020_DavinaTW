package com.uc.week4retrofit.repository

import com.uc.week4retrofit.retrofit.EndPointApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: EndPointApi) {
    suspend fun getNowPlayingData(apiKey:String, language:String, page:Int)
    = api.getNowPlaying(apiKey,language,page)

    suspend fun getMovieDetailResults(id:Int, apiKey:String)
    = api.getMovieDetails(id, apiKey)

}