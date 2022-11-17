package com.a90ms.openweather.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a90ms.common.covertTime
import com.a90ms.common.isValidContext
import com.a90ms.domain.data.dto.ListDto
import com.a90ms.domain.data.dto.MainDto
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

@BindingAdapter("bindList")
fun RecyclerView.bindList(item: List<Any>?) {
    if (item == null) return

    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseMultiViewAdapter<Any>)?.run {
        val newItems = mutableListOf<Any>()
        item.forEach {
            newItems.add(it)
        }
        submitList(newItems)
    }
}

@BindingAdapter("bindImage", "bindPlaceHolder")
fun ImageView.bindImage(
    url: String?,
    placeHolder: Drawable? = null
) {
    if (context.isValidContext()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().error(placeHolder).placeholder(placeHolder))
            .into(this)
    }
}

@BindingAdapter("bindTemp")
fun TextView.bindTemp(main: MainDto) {
    val max = main.temp_max.roundToInt()
    val min = main.temp_min.roundToInt()

    val value = "Max : $max°C   Min : $min°C"
    text = value
}

@BindingAdapter("bindDate")
fun TextView.bindDate(item: ListDto) {
    val value = item.shortDate.covertTime()
    text = value
}
