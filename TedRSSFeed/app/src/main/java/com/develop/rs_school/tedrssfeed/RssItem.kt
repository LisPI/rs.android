package com.develop.rs_school.tedrssfeed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RssItem(
    val title: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,
    val duration: String,
    //TODO list
    val speaker: List<String>
) : Parcelable