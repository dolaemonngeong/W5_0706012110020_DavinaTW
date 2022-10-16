package com.uc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.adapter.CompanyAdapter
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.LanguageAdapter
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var bind:ActivityMovieDetailBinding
    private lateinit var viewModel:MoviesViewModel
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterCompany: CompanyAdapter
    private lateinit var adapterLanguage: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val movieid = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "MovieID: $movieid", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetail(movieid, Const.API_KEY)
        viewModel.movieDetails.observe(this, Observer{
            response->
            bind.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterGenre = GenreAdapter(response.genres)
            bind.rvGenre.adapter = adapterGenre
            bind.tvTitleMovieDetail.apply{
                text = response.title
            }
            bind.rvCompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterCompany = CompanyAdapter(response.production_companies)
            bind.rvCompany.adapter = adapterCompany

            bind.rvLanguage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterLanguage = LanguageAdapter(response.spoken_languages)
            bind.rvLanguage.adapter = adapterLanguage

            bind.tvOverviewMovieDetail.apply{
                text = response.overview
            }

            Glide.with(applicationContext)
                .load(Const.IMG_URL + response.backdrop_path)
                .into(bind.imageView)
        })

        //panggil loading class
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                loading.isDismiss()
            }
        }, 5000)

    }
}