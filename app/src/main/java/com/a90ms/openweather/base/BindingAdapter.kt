package com.a90ms.openweather.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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
