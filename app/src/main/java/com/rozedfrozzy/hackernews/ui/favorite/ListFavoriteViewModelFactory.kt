package com.rozedfrozzy.hackernews.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper

class ListFavoriteViewModelFactory(val schedulerWrapper: SchedulerWrapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListFavoriteViewModel(schedulerWrapper) as T
    }
}