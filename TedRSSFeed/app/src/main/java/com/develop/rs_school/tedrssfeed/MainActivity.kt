package com.develop.rs_school.tedrssfeed

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.develop.rs_school.tedrssfeed.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), RssOverviewView {

    @InjectPresenter
    lateinit var presenter: RssOverviewPresenter

    private lateinit var binding: ActivityMainBinding

    // TODO MVP ?  in Presenter
    private val adapter = RssFeedRecyclerAdapter(RssRecyclerItemListener { rssItem ->
        presenter.rssItemClicked(rssItem)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
