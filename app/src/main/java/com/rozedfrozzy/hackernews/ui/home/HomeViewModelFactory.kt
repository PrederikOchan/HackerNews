package com.rozedfrozzy.hackernews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper

class HomeViewModelFactory(val schedulerWrapper: SchedulerWrapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(schedulerWrapper) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}