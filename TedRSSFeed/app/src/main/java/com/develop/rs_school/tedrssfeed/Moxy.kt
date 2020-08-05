package com.develop.rs_school.tedrssfeed

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.*
import org.json.JSONObject

@StateStrategyType(AddToEndSingleStrategy::class)
interface RssOverviewView : MvpView {
    fun showRssFeed(rssFeed: List<RssItem>)
    @StateStrategyType(SkipStrategy::class)
    fun goToDetailView(rssItem: RssItem)
}

//model in constructor?
class RssOverviewPresenter : MvpPresenter<RssOverviewView>() {
    init{
        viewState.showRssFeed(getRssItems())
    }
    //TODO pass id and in another screen get by id from repository
    fun rssItemClicked(rssItem: RssItem) {
        viewState.goToDetailView(rssItem)
    }
}

//TODO format - 00:01:43 to 01:43
//TODO refactor this
//GSON
fun getRssItems(): List<RssItem> {
    val rssItems = mutableListOf<RssItem>()
    val jsonString = App.instance.applicationContext.assets.open("data.json").bufferedReader()
        .use { it.readText() }
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
                title = itemsArray.getJSONObject(i).getString("title").substringBeforeLast("|"),
                description = itemsArray.getJSONObject(i).getString("description"),
                imageUrl = itemsArray.getJSONObject(i).getJSONObject("image")
                    .getString("url"),
                videoUrl = itemsArray.getJSONObject(i).getJSONObject("enclosure")
                    .getString("url"),
                duration = itemsArray.getJSONObject(i).getJSONObject("duration")
                    .getString("text"),
                speaker = speakers
            )
        )
    }
    return rssItems
}