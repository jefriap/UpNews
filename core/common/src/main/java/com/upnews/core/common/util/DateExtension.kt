package com.upnews.core.common.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LocaleDefault {
    fun indonesia() = Locale("in", "ID")
}

fun String.getFormatDate(
    locale: Locale = LocaleDefault.indonesia(),
    oldPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
    newPattern: String = "dd MMMM yyyy",
    emptyValue: String = "-"
): String {
    return try {
        val oldDateInSecond = this.dateToTimeSecond(oldPattern)
        val newDate = SimpleDateFormat(newPattern, locale)
        val string = this
        if (string.isEmpty()) emptyValue
        else newDate.format(Date(oldDateInSecond))
    } catch (e: Exception) {
        emptyValue
    }
}

fun String.dateToTimeSecond(pattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'") =
    if (this == "") 0 else SimpleDateFormat(pattern, LocaleDefault.indonesia()).parse(this)?.time ?: 0