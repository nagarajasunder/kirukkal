package com.geekydroid.kirukkal.core.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateTimeUtils {

    fun convertMillisToDateTimeFormat(millis:Long,format:DATE_TIME_FORMATS) : String {
        val formatStr = getFormat(format)
        val sdf = SimpleDateFormat(formatStr, Locale.ENGLISH)
        return sdf.format(millis)
    }

    private fun getFormat(format: DATE_TIME_FORMATS) : String {
        return when(format) {
            DATE_TIME_FORMATS.HH_MM_AA -> "HH:mm a"
        }
    }
}

enum class DATE_TIME_FORMATS {
    HH_MM_AA
}