package com.develop.rs_school.tedrssfeed

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.json.JSONArray
import org.json.JSONObject

@StateStrategyType(AddToEndSingleStrategy::class)
interface RssOverviewView : MvpView {
    fun showRssFeed(rssFeed: List<RssItem>)
}

//model in constructor?
class RssOverviewPresenter : MvpPresenter<RssOverviewView>() {
    init {
        viewState.showRssFeed(getRssItems())
    }
}

//TODO format - 00:01:43 to 01:43
//TODO textView shapeLayout for round corner
//TODO refactor this
fun getRssItems(): List<RssItem> {
    val rssItems = mutableListOf<RssItem>()
    val jsonString = App.instance.applicationContext.assets.open("data.json").bufferedReader()
        .use { it.readText() }
    val itemsArray = JSONObject(jsonString).getJSONObject("channel").getJSONArray("item")

    for (i in 0 until itemsArray.length()) {
        //TODO refactor this - "and" between speakers, cat speaker from title
        val credits = itemsArray.getJSONObject(i).getJSONObject("group")
        val speaker: String =
            if (credits.optJSONArray("credit") != null) {
                var buffer = ""
                for (j in 0 until credits.getJSONArray("credit").length())
                    buffer = buffer.plus(credits.getJSONArray("credit").getJSONObject(j).getString("text") + " and ")
                buffer.substringBeforeLast(" and ")
            } else
                credits.getJSONObject("credit").getString("text")

        rssItems.add(
            RssItem(
                title = itemsArray.getJSONObject(i).getString("title").substringBeforeLast("|"),
                description = itemsArray.getJSONObject(i).getString("description"),
                imageUrl = itemsArray.getJSONObject(i).getJSONObject("image")
                    .getString("url"),
                videoUrl = itemsArray.getJSONObject(i).getJSONObject("enclosure")
                    .getString("url"),
                duration = itemsArray.getJSONObject(i).getJSONObject("duration")
                    .getString("text"),
                speaker = speaker
            )
        )
    }
    return rssItems
}