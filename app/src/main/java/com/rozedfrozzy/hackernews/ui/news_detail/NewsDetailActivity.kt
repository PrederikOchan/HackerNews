package com.rozedfrozzy.hackernews.ui.news_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.rozedfrozzy.hackernews.R
import com.rozedfrozzy.hackernews.util.NetworkStatus
import kotlinx.android.synthetic.main.activity_news_detail.*
import org.jetbrains.anko.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class NewsDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    companion object {
        const val NEWS_URL = "news_url"
    }
    private var networkStatus = NetworkStatus(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbarNewsDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getUrl: Bundle? = intent.extras

        getUrl?.let {
            if (networkStatus.isOnline())
                newsDetail.loadUrl(getUrl.getString(NEWS_URL))
            else
                toast(getString(R.string.no_internet_connection))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
