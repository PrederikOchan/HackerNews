package com.rozedfrozzy.hackernews.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.data.network.NetworkService
import com.rozedfrozzy.hackernews.data.network.SchedulerWrapper
import com.rozedfrozzy.hackernews.data.db.entity.Story
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

class HomeViewModel(val schedulerWrapper: SchedulerWrapper) : ViewModel() {
    val resultListNewsItems = MutableLiveData<List<Story>>()
    val resultListErrorObservable = MutableLiveData<HttpException>()

    fun getResultListObservable(): LiveData<List<Story>> = resultListNewsItems

    private var listIds: ArrayList<Long> = ArrayList()
    private var listNews: ArrayList<Story> = ArrayList()
    lateinit var networkService: NetworkService
    lateinit var database: FavoriteDatabase

    @SuppressLint("CheckResult")
    fun getNews() {
        networkService.fetchTopNews()!!
            .subscribeOn(schedulerWrapper.io())
            .observeOn(schedulerWrapper.main())
            .subscribeWith(object: DisposableSingleObserver<List<Long>?>() {
                override fun onSuccess(t: List<Long>) {
                    listIds.clear()
                    listIds.addAll(t)

                    getNewsDetail(listIds)
                }

                override fun onError(e: Throwable) {
                    resultListErrorObservable.postValue(e as HttpException)
                }

            })
    }

    private fun getNewsDetail(listIds: ArrayList<Long>) {
        listIds.forEach {
            networkService.fetchNewsById(it.toString())!!
                .subscribeOn(schedulerWrapper.io())
                .observeOn(schedulerWrapper.main())
                .subscribeWith(object: DisposableSingleObserver<Story?>() {
                    override fun onSuccess(t: Story) {
                        resultListNewsItems.postValue(fetchItemFromResult(t))
                    }

                    override fun onError(e: Throwable) {
                        resultListErrorObservable.postValue(e as HttpException)
                    }

                })
        }
    }

    private fun fetchItemFromResult(t: Story): ArrayList<Story>? {
        listNews = ArrayList()
        listNews.add(t)
        return listNews
    }

}