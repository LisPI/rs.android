package com.develop.rs_school.tedrssfeed

import android.content.Intent
import android.os.Bundle
import com.develop.rs_school.tedrssfeed.databinding.ActivityMainBinding
import com.develop.rs_school.tedrssfeed.network.RssApi
import com.develop.rs_school.tedrssfeed.network.RssFeedXMLService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import okhttp3.Dispatcher

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

        binding.rssRecycler.adapter = adapter


        CoroutineScope(Dispatchers.Main).launch {
            with(Dispatchers.IO) {
                val feed = RssApi.retrofitService.getXML()
                val t = feed.itemList.map { result ->
                    RssItem(
                        title = result.title,
                        description = result.description.substringBeforeLast("|"),
                        imageUrl = result.image.url,
                        videoUrl = result.video.url,
                        duration = result.duration,
                        speaker = result.credit.map { it.speaker }
                    )
                }
                t[2]

            }
        }

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
