package com.example.testinterviewsalinas.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.testinterviewsalinas.model.MovieModel


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSingle(movie: MovieModel)


}