package com.develop.rs_school.tedrssfeed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RssItem(
    val title: String,
    //TODO list
    val speaker: String,
    val duration: String,
    val imageUrl: String,
    val videoUrl: String,
    val description: String
) : Parcelable