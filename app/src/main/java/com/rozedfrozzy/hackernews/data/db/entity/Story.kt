package com.rozedfrozzy.hackernews.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_news")
data class Story(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("by")
    val by: String,
    @SerializedName("descendants")
    val descendants: Int,
    @SerializedName("score")
    val score: Int,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)