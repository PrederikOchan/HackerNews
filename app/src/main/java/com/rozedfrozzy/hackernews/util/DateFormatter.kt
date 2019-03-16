package com.rozedfrozzy.hackernews.util

import java.text.SimpleDateFormat
import java.util.Date


class DateFormatter(date: Long) {
    var dateInMillis: Long = date*1000

    fun getFormattedDate(): String {
        var date = Date(dateInMillis)
        var sdf = SimpleDateFormat("MMM dd, yyyy")
        return sdf.format(date)
    }
}