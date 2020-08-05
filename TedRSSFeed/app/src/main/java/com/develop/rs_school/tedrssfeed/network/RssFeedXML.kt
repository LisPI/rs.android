package com.develop.rs_school.tedrssfeed.network

import com.tickaroo.tikxml.annotation.*

@Xml
data class RssFeed(
    @Path("channel")
    @PropertyElement(name = "title") val title: String,
    @Path("channel")
    @Element(name = "item") val itemList: List<Item>
)

@Xml
data class Item(
    @PropertyElement(name = "title") val title: String,
    @PropertyElement(name = "description") val description: String,
    @PropertyElement(name = "itunes:duration") val duration: String,
    @Element(name = "itunes:image") val image: ImageData,
    @Element(name = "enclosure") val video: VideoData,
    @Path("media:group")
    @Element(name = "media:credit") val credit: List<CreditData>
)

@Xml
data class ImageData(@Attribute val url: String)

@Xml
data class VideoData(@Attribute val url: String)

@Xml
data class CreditData(@TextContent val speaker: String)
