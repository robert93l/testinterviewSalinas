package com.example.testinterviewsalinas.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testinterviewsalinas.model.LocationModel
import com.example.testinterviewsalinas.repository.Repository

import javax.inject.Inject

class LocationViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val locationsListData: LiveData<List<LocationModel>> = repository.locationListData

    fun getLocations() {
        repository.getLocations()
    }
}