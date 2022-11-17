package com.a90ms.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

const val DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd"

fun commonSdf(pattern: String) = SimpleDateFormat(pattern, Locale.KOREA)

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

fun String.covertTime(): String {
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val date = stringToDateParser(DATE_PATTERN_yyyy_MM_dd)
    val dateDiff = date.time - today.time.time
    val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

    return when {
        date.time - today.time.time < 0 -> "오늘"
        day == 0L -> "내일"
        else -> {
            "${date.dayOfWeekLabel()} ${substring(5)}"
        }
    }
}
