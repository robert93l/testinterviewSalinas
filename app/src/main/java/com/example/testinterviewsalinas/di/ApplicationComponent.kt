package com.example.testinterviewsalinas.di

import android.content.Context
import com.example.testinterviewsalinas.MainActivity
import com.example.testinterviewsalinas.ui.storage.StorageFragment
import com.example.testinterviewsalinas.ui.location.LocationFragment
import com.example.testinterviewsalinas.ui.movies.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Singleton
@Component(modules = [RoomModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun testComponent(): TestComponent.Factory
}

@ActivityScope
@Subcomponent
interface TestComponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): TestComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: StorageFragment)
    fun inject(fragment: LocationFragment)
}