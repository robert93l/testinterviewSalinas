package com.example.testinterviewsalinas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class MovieModel(
        var adult: Boolean,
        @SerializedName("backdrop_path") var backdropPath: String?,
        @PrimaryKey(autoGenerate = false)
        @SerializedName("id") var id: Int,
        @SerializedName("original_language") var originalLanguage: String,
        @SerializedName("original_title") var originalTitle: String,
        var overview: String,
        @SerializedName("poster_path") var posterPath: String,
        @SerializedName("release_date") var releaseDate: String,
        var title: String,
    ) : Serializable