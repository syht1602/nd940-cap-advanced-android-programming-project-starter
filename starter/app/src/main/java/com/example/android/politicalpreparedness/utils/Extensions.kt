package com.example.android.politicalpreparedness.utils

import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"

fun String.parseDate(format: String): String {
    var dateString = "Unknown time"
    this.let {
        try {
            val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
            dateFormat.parse(it)?.let { date ->
                dateString = date.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return dateString
}