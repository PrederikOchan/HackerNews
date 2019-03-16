package com.rozedfrozzy.hackernews

import android.app.Application
import com.rozedfrozzy.hackernews.data.db.FavoriteDatabase
import com.rozedfrozzy.hackernews.ui.favorite.ListFavoriteViewModelFactory
import com.rozedfrozzy.hackernews.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class HackerNewsApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@HackerNewsApplication))

        bind() from singleton { FavoriteDatabase(instance()) }
        bind() from singleton { instance<FavoriteDatabase>().FavoriteNewsDao() }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { ListFavoriteViewModelFactory(instance()) }
    }
}