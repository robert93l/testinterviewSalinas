package com.example.testinterviewsalinas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "Movies")
data class Movies (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("page") var code: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int,
    var results: List<MovieModel>,
): Serializable