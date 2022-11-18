package com.example.testinterviewsalinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.testinterviewsalinas.databinding.ActivityMovieDetailBinding
import com.example.testinterviewsalinas.model.MovieModel
import com.example.testinterviewsalinas.utils.Constants

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieDetailBinding
    private lateinit var movie : MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movie = intent.getSerializableExtra("movie") as MovieModel
        initToolbar()

        binding.collapsingToolbarMaterial.title = movie.title
        Glide.with(this).load(Constants.urlBaseImage + movie.backdropPath).centerCrop().into(binding.backdropImv)
        binding.OverviewTv.text = movie.overview
        binding.RelaseDateTv.text = movie.releaseDate

    }

    private fun initToolbar(){
        setSupportActionBar(binding.toolbarMaterial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}