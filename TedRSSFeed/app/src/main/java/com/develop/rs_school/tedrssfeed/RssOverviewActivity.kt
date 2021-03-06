package com.develop.rs_school.tedrssfeed

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.develop.rs_school.tedrssfeed.databinding.ActivityRssOverviewBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RssOverviewActivity : MvpAppCompatActivity(), RssOverviewView {

    @InjectPresenter
    lateinit var presenter: RssOverviewPresenter

    @ProvidePresenter
    fun providePresenter() = RssOverviewPresenter()

    private lateinit var binding: ActivityRssOverviewBinding

    private val adapter = RssFeedRecyclerAdapter(RssRecyclerItemListener { rssItem ->
        presenter.rssItemClicked(rssItem)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRssOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rssRecycler.adapter = adapter
    }

    override fun showRssFeed(rssFeed: List<RssItem>) {
        adapter.submitList(rssFeed)
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

    override fun showActualSource(sourceName: String) {
        Toast.makeText(this, sourceName, Toast.LENGTH_SHORT).show()
    }

    override fun goToDetailView(rssItem: RssItem) {
        val intent = Intent(this, RssItemDetailActivity::class.java)
        intent.putExtra(getString(R.string.rss_item_intent_key), rssItem)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.json_menu_item -> {
                presenter.switchSource()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
