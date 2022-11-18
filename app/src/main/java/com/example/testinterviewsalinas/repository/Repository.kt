package com.example.testinterviewsalinas.repository

import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testinterviewsalinas.model.LocationModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor() {

    private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference

    private var _uploadLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val uploadLiveData: LiveData<Boolean> get() = _uploadLiveData

    private var _locationListData: MutableLiveData<List<LocationModel>> = MutableLiveData()
    val locationListData: LiveData<List<LocationModel>> get() = _locationListData

    private var _storageListData: MutableLiveData<List<String>> = MutableLiveData()
    val storageListData: LiveData<List<String>> get() = _storageListData

    fun uploadLocation(locationModel: Location?) {

        val newLocation = hashMapOf(
            "longitude" to locationModel?.longitude,
            "latitude" to locationModel?.latitude,
            "date" to FieldValue.serverTimestamp()
        )

        db.collection("locations").add(newLocation)
    }

    fun getLocations()
    {
        db.collection("locations")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val locationList = mutableListOf<LocationModel>()
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        locationList.add(document.toObject(LocationModel::class.java)!!)
                    }
                }
                _locationListData.value = locationList
            }
    }

    fun getStorage()
    {
        db.collection("urls")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val urlList = mutableListOf<String>()
                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data?.values}")
                        urlList.add(document.data?.values.toString())
                    }
                }
                _storageListData.value = urlList
            }
    }


    fun uploadImage(uri: Uri, context: Context) {

        val imageName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/${imageName}")

        val metadata = storageMetadata {
            contentType = "image/jpg"
        }

        val uploadTask = imageRef.putFile(uri, metadata)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _uploadLiveData.value = true
                val downloadUri = task.result
                val newurl = hashMapOf(
                    "url" to downloadUri.toString(),
                )
                db.collection("urls").add(newurl)
            } else {
                _uploadLiveData.value = false
            }
        }
    }
}