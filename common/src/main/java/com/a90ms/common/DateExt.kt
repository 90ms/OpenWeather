package com.a90ms.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd"

fun commonSdf(pattern: String) = SimpleDateFormat(pattern, Locale.KOREA)

fun String.convertTime(): String =
    commonSdf(DATE_PATTERN_yyyy_MM_dd).parse(this)?.let { date ->

        val today = Calendar.getInstance().time.time
        val dateDiff = (date.time - today)

        val label = stringToDateParser(DATE_PATTERN_yyyy_MM_dd).dayOfWeekLabel()

        return when {
            dateDiff < 1 -> "오늘"
            dateDiff == 1L -> "내일"
            else -> "$label ${this.substring(5, 10).replace("-", " ")}"
        }
    } ?: run {
        return this
    }

fun String.stringToDateParser(pattern: String): Date = commonSdf(pattern).parse(this) as Date

fun Date.dayOfWeekLabel() =
    when (Calendar.getInstance().apply { time = this@dayOfWeekLabel }.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "월"
        Calendar.TUESDAY -> "화"
        Calendar.WEDNESDAY -> "수"
        Calendar.THURSDAY -> "목"
        Calendar.FRIDAY -> "금"
        Calendar.SATURDAY -> "토"
        else -> "일"
    }
