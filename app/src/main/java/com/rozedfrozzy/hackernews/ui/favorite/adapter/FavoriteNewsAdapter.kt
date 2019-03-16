package com.rozedfrozzy.hackernews.ui.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rozedfrozzy.hackernews.R
import com.rozedfrozzy.hackernews.data.db.entity.Story
import kotlinx.android.synthetic.main.list_favorite_item.view.*

class FavoriteNewsAdapter(val context: Context) : RecyclerView.Adapter<FavoriteNewsAdapter.ViewHolder>() {

    private var newsList: ArrayList<Story> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = newsList[position]
        holder.setModel(model)
    }

    fun updateList(list: List<Story>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setModel(model: Story) {
            model?.title?.let { itemView.favoriteNewsTitle.text = model.title }
        }
    }
}