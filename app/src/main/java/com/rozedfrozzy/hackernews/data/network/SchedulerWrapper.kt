package com.rozedfrozzy.hackernews.data.network

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class SchedulerWrapper {
    open fun io(): Scheduler {
        return Schedulers.io()
    }

    open fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}