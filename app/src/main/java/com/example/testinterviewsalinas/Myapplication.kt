package com.example.testinterviewsalinas

import android.app.Application
import com.example.testinterviewsalinas.di.ApplicationComponent
import com.example.testinterviewsalinas.di.DaggerApplicationComponent


class MyApplication: Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.factory().create(this)
}