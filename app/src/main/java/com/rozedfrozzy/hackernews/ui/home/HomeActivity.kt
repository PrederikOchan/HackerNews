package com.rozedfrozzy.hackernews.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rozedfrozzy.hackernews.R
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.data.network.NetworkService
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper
import com.rozedfrozzy.hackernews.ui.favorite.ListFavoriteActivity
import com.rozedfrozzy.hackernews.ui.home.adapter.NewsItemAdapter
import com.rozedfrozzy.hackernews.util.NetworkStatus
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: FavoriteDatabase
    private lateinit var adapter: NewsItemAdapter
    private var networkStatus = NetworkStatus(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, HomeViewModelFactory(SchedulerWrapper())).get(HomeViewModel::class.java)
        viewModel.networkService = NetworkService()
        viewModel.database = FavoriteDatabase(this)
        database = viewModel.database

        getNewsData()
        configureRecyclerView()
        observableListener()
    }

    private fun getNewsData() {
        if (networkStatus.isOnline()){
            viewModel.getNews()
            showProgressBar()
        } else {
            toast(getString(R.string.no_internet_connection))
        }
    }

    private fun observableListener() {
        viewModel.getResultListObservable().observe(this, Observer {
            adapter.updateList(it!!)
            hideProgressBar()
        })
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val itemDivider = DividerItemDecoration(this, layoutManager.orientation)
        adapter = NewsItemAdapter(this, database)
        recyclerViewTopNews.layoutManager = layoutManager
        recyclerViewTopNews.addItemDecoration(itemDivider)
        recyclerViewTopNews.adapter = adapter
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            startActivity<ListFavoriteActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
