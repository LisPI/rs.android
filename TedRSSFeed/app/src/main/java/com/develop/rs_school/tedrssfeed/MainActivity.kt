package com.develop.rs_school.tedrssfeed

import android.content.Intent
import android.os.Bundle
import com.develop.rs_school.tedrssfeed.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainActivity : MvpAppCompatActivity(), RssOverviewView {

    @InjectPresenter
    lateinit var presenter: RssOverviewPresenter

    //private val model?
    @ProvidePresenter
    fun providePresenter() = RssOverviewPresenter()

    private lateinit var binding: ActivityMainBinding

    private val adapter = RssFeedRecyclerAdapter(RssRecyclerItemListener { rssItem ->
        presenter.rssItemClicked(rssItem)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //https://www.ted.com/themes/rss/id
        binding.rssRecycler.adapter = adapter
    }

    override fun showRssFeed(rssFeed: List<RssItem>) {
        adapter.submitList(rssFeed)
    }

    override fun goToDetailView(rssItem: RssItem) {
        val intent = Intent(this, RssItemDetailActivity::class.java)
        intent.putExtra("item", rssItem)
        startActivity(intent)
    }

}
