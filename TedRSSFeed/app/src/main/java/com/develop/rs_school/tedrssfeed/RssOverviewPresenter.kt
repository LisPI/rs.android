package com.develop.rs_school.tedrssfeed

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

// TODO DI Model
class RssOverviewPresenter constructor(
    private var model: RssOverviewModel = ModelJson()
) : MvpPresenter<RssOverviewView>() {

    override fun onFirstViewAttach() {
        loadData()
    }

    fun rssItemClicked(rssItem: RssItem) {
        viewState.goToDetailView(rssItem)
    }

    // FIXME for switch between source
    fun switchSource() {
        when (model) {
            is ModelXML -> {
                model = ModelJson()
                viewState.showActualSource("Json")
            }
            is ModelJson -> {
                model = ModelXML()
                viewState.showActualSource("XML")
            }
        }
        loadData()
    }

    private fun loadData() {
        presenterScope.launch {
            try {
                viewState.showRssFeed(model.getRssItems())
            } catch (e: Exception) {
                viewState.showError()
                viewState.showRssFeed(listOf())
            }
        }
    }
}
