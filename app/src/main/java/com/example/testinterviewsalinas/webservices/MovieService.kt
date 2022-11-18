package com.example.testinterviewsalinas.webservices

import com.example.testinterviewsalinas.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/top_rated")
    suspend fun getMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("languaje") languaje: String
    ): Response<Movies>
}