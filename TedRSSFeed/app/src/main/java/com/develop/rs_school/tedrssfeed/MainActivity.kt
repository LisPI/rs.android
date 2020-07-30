package com.develop.rs_school.tedrssfeed

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.develop.rs_school.tedrssfeed.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.json.JSONObject

class MainActivity : MvpAppCompatActivity(), ViewMVP, RssOverviewView {

    @InjectPresenter
    lateinit var presenter: RssOverviewPresenter

    //private val model?
    @ProvidePresenter
    fun providePresenter() = RssOverviewPresenter()

    private lateinit var binding: ActivityMainBinding

    private val adapter = RssFeedRecyclerAdapter(RssRecyclerItemListener { rssItem ->
        //viewModel.onCatClicked(rssItem)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //        for test videoView
//        val vv = findViewById<VideoView>(R.id.videoView)
//        vv.setVideoPath(itemsArray.getJSONObject(0).getJSONObject("enclosure").getString("url"))
//        vv.setMediaController(MediaController(this));
//        vv.requestFocus()
//        vv.start()

        //https://www.ted.com/themes/rss/id
/*
        val model : ModelMVP = ModelJSON()
        val presenter = Presenter(model, this)

        findViewById<Button>(R.id.button).setOnClickListener {
            presenter.getItemButtonClicked(applicationContext)
        }*/

        binding.rssRecycler.adapter = adapter
    }

    override fun showItem(item: String) {
        findViewById<TextView>(R.id.text).text = item
    }

    override fun showRssFeed(rssFeed: List<RssItem>) {
        adapter.submitList(rssFeed)
    }
}
