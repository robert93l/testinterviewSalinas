package com.example.testinterviewsalinas.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testinterviewsalinas.model.MovieModel


@Database(entities = [MovieModel::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    abstract val moviesDAO: MoviesDao

    companion object{

        @Volatile
        private var INSTANCE: MoviesDataBase? = null

        fun getInstance(context: Context): MoviesDataBase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDataBase::class.java,
                        "movies_history_database"
                    ).build()

                    INSTANCE = instance
                    return instance
                }
                return instance
            }
        }
    }
}