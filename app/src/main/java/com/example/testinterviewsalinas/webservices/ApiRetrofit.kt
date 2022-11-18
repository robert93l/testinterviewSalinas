package com.example.testinterviewsalinas.webservices

import com.example.testinterviewsalinas.utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRetrofit {

    val movieService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(MovieService::class.java)
    }
}