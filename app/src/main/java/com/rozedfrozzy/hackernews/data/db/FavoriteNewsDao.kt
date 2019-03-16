package com.rozedfrozzy.hackernews.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rozedfrozzy.hackernews.data.db.entity.Story

@Dao
interface FavoriteNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteNews: Story)

    @Query("select * from favorite_news")
    fun getFavoriteNews(): LiveData<List<Story>>

    @Query("delete from favorite_news where id like :newsId")
    fun deleteFavorite(newsId: Int)
}