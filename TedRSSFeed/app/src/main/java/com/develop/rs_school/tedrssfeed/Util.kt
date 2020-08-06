package com.develop.rs_school.tedrssfeed

fun getTitle(titleWithSpeaker: String) = titleWithSpeaker.substringBeforeLast("|")

fun getSpeakers(speakers: List<String>)= speakers.joinToString(separator = " and ")

fun formatDuration(duration: String) = if(duration.startsWith("00:")) duration.substring(3) else duration