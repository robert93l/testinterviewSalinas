package com.example.testinterviewsalinas.di

import android.content.Context
import com.example.testinterviewsalinas.db.MoviesDao
import com.example.testinterviewsalinas.db.MoviesDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideMovieDao(context: Context): MoviesDao {

        return MoviesDataBase.getInstance(context).moviesDAO
    }
}

@Module(subcomponents = [TestComponent::class])
class SubcomponentsModule {}