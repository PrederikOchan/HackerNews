package com.rozedfrozzy.hackernews.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rozedfrozzy.hackernews.R
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.data.db.entity.Story
import com.rozedfrozzy.hackernews.ui.news_detail.NewsDetailActivity
import com.rozedfrozzy.hackernews.util.DateFormatter
import kotlinx.android.synthetic.main.list_news_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity

class NewsItemAdapter(
    val context: Context,
    val database: FavoriteDatabase
) : RecyclerView.Adapter<NewsItemAdapter.ViewHolder>() {

    private var newsList: ArrayList<Story> = ArrayList()
    private var isFavorite = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = newsList[position]
        holder.setModel(model)
        holder.newsItemLayout.setOnClickListener {
            context.startActivity<NewsDetailActivity>(NewsDetailActivity.NEWS_URL to model.url)
        }
        holder.btnFavorite.setOnClickListener {
            if (!isFavorite) {
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp)
                GlobalScope.launch(Dispatchers.IO) {
                    database.FavoriteNewsDao().insert(model)
                }
                isFavorite = true
            } else {
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border_red_24dp)
                GlobalScope.launch {
                    database.FavoriteNewsDao().deleteFavorite(model.id)
                }
                isFavorite = false
            }
        }
    }

    fun updateList(list: List<Story>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        newsList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsItemLayout: ConstraintLayout = itemView.newsItem
        val btnFavorite = itemView.btnFavorite

        @SuppressLint("SetTextI18n")
        fun setModel(model: Story) {
            model?.id?.let { itemView.newsId.text = "#${model.id}" }
            model?.title?.let { itemView.newsTitle.text = model.title }
            model?.by?.let { itemView.newsAuthor.text = "Author: ${model.by}" }
            model?.time?.let { itemView.newsDate.text = DateFormatter(model.time).getFormattedDate() }
        }
    }
}