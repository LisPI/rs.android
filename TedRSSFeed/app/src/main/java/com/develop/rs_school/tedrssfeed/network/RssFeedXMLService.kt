package com.develop.rs_school.tedrssfeed.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://www.ted.com/"

@Xml
data class Rss(
    @Path("rss/channel")
    @PropertyElement(name = "title") val rss: String?
)

interface RssFeedXMLService {
    @GET("themes/rss/id")
    suspend fun getXML(): Rss
}

object RssApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(TikXmlConverterFactory.create(
            TikXml.Builder().exceptionOnUnreadXml(false).build()
        ))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: RssFeedXMLService = retrofit.create(
        RssFeedXMLService::class.java)
}