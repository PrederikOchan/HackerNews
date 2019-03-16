package com.rozedfrozzy.hackernews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rozedfrozzy.hackernews.data.db.entity.Story


@Database(
    entities = [Story::class],
    version = 1
)

abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun FavoriteNewsDao(): FavoriteNewsDao

    companion object {
        @Volatile private var instance: FavoriteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                FavoriteDatabase::class.java, "favorite.db")
                .build()
    }
}