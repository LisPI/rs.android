package com.develop.rs_school.tedrssfeed

import com.develop.rs_school.tedrssfeed.network.RssApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

interface RssOverviewModel {
    suspend fun getRssItems(): List<RssItem>
}

class ModelXML : RssOverviewModel {
    override suspend fun getRssItems() =
        with(Dispatchers.IO) {
            val rssFeed = RssApi.retrofitService.getXML()
            rssFeed.itemList.map { result ->
                RssItem(
                    title = result.title.replace("&quot;", ""),
                    description = result.description,
                    imageUrl = result.image.url.replace("amp;", ""),
                    videoUrl = result.video.url,
                    duration = result.duration,
                    speakers = result.credit.map { it.speaker }
                )
            }
        }
}

class ModelJson : RssOverviewModel {
    override suspend fun getRssItems(): List<RssItem> {
        val rssItems = mutableListOf<RssItem>()
        val jsonString = withContext(Dispatchers.IO) {
            App.instance.applicationContext.assets.open("data.json").bufferedReader()
                .use { it.readText() }
        }
        val itemsArray = JSONObject(jsonString).getJSONObject("channel").getJSONArray("item")

        for (i in 0 until itemsArray.length()) {
            val credits = itemsArray.getJSONObject(i).getJSONObject("group")
            val speakers: MutableList<String> = mutableListOf()

            if (credits.optJSONArray("credit") != null) {
                for (j in 0 until credits.getJSONArray("credit").length())
                    speakers.add(credits.getJSONArray("credit").getJSONObject(j).getString("text"))
            } else
                speakers.add(credits.getJSONObject("credit").getString("text"))

            rssItems.add(
                RssItem(
                    title = itemsArray.getJSONObject(i).getString("title"),
                    description = itemsArray.getJSONObject(i).getString("description"),
                    imageUrl = itemsArray.getJSONObject(i).getJSONObject("image")
                        .getString("url"),
                    videoUrl = itemsArray.getJSONObject(i).getJSONObject("enclosure")
                        .getString("url"),
                    duration = itemsArray.getJSONObject(i).getJSONObject("duration")
                        .getString("text"),
                    speakers = speakers
                )
            )
        }
        return rssItems
    }
}