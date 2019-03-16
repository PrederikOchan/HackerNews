package com.rozedfrozzy.hackernews.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rozedfrozzy.hackernews.R
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper
import com.rozedfrozzy.hackernews.ui.favorite.adapter.FavoriteNewsAdapter
import kotlinx.android.synthetic.main.activity_list_favorite.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ListFavoriteActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var database: FavoriteDatabase
    private lateinit var viewModel: ListFavoriteViewModel
    private var adapter = FavoriteNewsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_favorite)
        setSupportActionBar(toolbarFavorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.favorite)

        viewModel = ViewModelProviders.of(this, ListFavoriteViewModelFactory(SchedulerWrapper())).get(ListFavoriteViewModel::class.java)
        viewModel.initFavoriteDatabase = FavoriteDatabase(this)
        database = viewModel.initFavoriteDatabase

        observerListener()

        configureRecyclerView()
    }

    private fun observerListener() {
        viewModel.getFavoriteNews(database)?.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    private fun configureRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val itemDivider = DividerItemDecoration(this, layoutManager.orientation)
        listFavoriteNews.layoutManager = layoutManager
        listFavoriteNews.addItemDecoration(itemDivider)
        listFavoriteNews.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
