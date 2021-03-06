package com.develop.rs_school.tedrssfeed

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RssOverviewView : MvpView {
    fun showRssFeed(rssFeed: List<RssItem>)

    @StateStrategyType(SkipStrategy::class)
    fun showError()

    @StateStrategyType(SkipStrategy::class)
    fun showActualSource(sourceName: String)

    @StateStrategyType(SkipStrategy::class)
    fun goToDetailView(rssItem: RssItem)
}
