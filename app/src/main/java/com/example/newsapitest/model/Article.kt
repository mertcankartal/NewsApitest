package com.example.newsapitest.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: @RawValue Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Parcelable