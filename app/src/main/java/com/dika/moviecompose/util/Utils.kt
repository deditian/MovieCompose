package com.dika.moviecompose.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}

fun textMax(title: String) : String {
    val maxLength = 15
    val parsedTitle = if (title.length > maxLength) {
        title.take(maxLength) + "..."
    } else {
        title
    }
    return parsedTitle
}