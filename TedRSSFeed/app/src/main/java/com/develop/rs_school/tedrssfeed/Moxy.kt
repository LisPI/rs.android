package com.develop.rs_school.tedrssfeed

import com.develop.rs_school.tedrssfeed.network.RssApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import org.json.JSONObject

@StateStrategyType(AddToEndSingleStrategy::class)
interface RssOverviewView : MvpView {
    fun showRssFeed(rssFeed: List<RssItem>)

    @StateStrategyType(SkipStrategy::class)
    fun goToDetailView(rssItem: RssItem)
}

// TODO DI
class RssOverviewPresenter constructor(
    private var model: ModelMVP
) : MvpPresenter<RssOverviewView>() {

    override fun onFirstViewAttach() {
        presenterScope.launch {
            viewState.showRssFeed(model.getRssItems())
        }
    }

    fun rssItemClicked(rssItem: RssItem) {
        viewState.goToDetailView(rssItem)
    }

    // FIXME for switch between source
    fun switchSource() {
        model = if (model is ModelXML) ModelJson() else ModelXML()
        presenterScope.launch {
            viewState.showRssFeed(model.getRssItems())
        }
    }
}

interface ModelMVP {
    suspend fun getRssItems(): List<RssItem>
}

class ModelXML : ModelMVP {
    override suspend fun getRssItems() =
        with(Dispatchers.IO) {
            val rssFeed = RssApi.retrofitService.getXML()
            rssFeed.itemList.map { result ->
                RssItem(
                    title = result.title.replace("&quot;", ""),
                    description = result.description,
                    imageUrl = result.image.url.replace("amp;", ""),
                    videoUrl = result.video.url,
                    duration = "00", // result.duration,
                    speakers = result.credit.map { it.speaker }
                )
            }
        }
}

class ModelJson : ModelMVP {
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
