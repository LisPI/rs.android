package com.develop.rs_school.tedrssfeed

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

// TODO DI Model
class RssOverviewPresenter constructor(
    private var model: RssOverviewModel
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
            }
            is ModelJson -> {
                model = ModelXML()
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