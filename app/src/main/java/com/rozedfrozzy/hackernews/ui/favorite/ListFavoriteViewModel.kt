package com.rozedfrozzy.hackernews.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.data.db.entity.Story
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFavoriteViewModel(val schedulerWrapper: SchedulerWrapper) : ViewModel() {
    var resultFavoriteNews: LiveData<List<Story>>? = null

    lateinit var initFavoriteDatabase: FavoriteDatabase

    fun getFavoriteNews(favoriteDatabase: FavoriteDatabase): LiveData<List<Story>>? {
        GlobalScope.launch(Dispatchers.IO){
            resultFavoriteNews = favoriteDatabase.FavoriteNewsDao().getFavoriteNews()
        }
        return resultFavoriteNews
    }

}
