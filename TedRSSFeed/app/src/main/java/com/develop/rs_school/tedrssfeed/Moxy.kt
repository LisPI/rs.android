package com.develop.rs_school.tedrssfeed

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RssOverviewView: MvpView {
    fun showRssFeed(rssFeed: List<RssItem>)
}

//model in constructor?
class RssOverviewPresenter: MvpPresenter<RssOverviewView>(){
    init {
        viewState.showRssFeed(listOf(RssItem("Great cars are great art | Chris Bangle", "https://pi.tedcdn.com/r/pe.tedcdn.com/images/ted/e98a047229351dbda3f53fb5a70102f2daf48c4d_800x600.jpg?w=615&h=461")))
    }
}