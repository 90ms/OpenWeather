package com.a90ms.openweather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.a90ms.common.toast
import com.a90ms.openweather.base.BaseActivity
import com.a90ms.openweather.base.BaseMultiViewAdapter
import com.a90ms.openweather.base.ViewHolderType
import com.a90ms.openweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel)
        setupObserver()
        setupRecyclerView()
        setupData()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
            srl.setOnRefreshListener {
                binding.srl.isRefreshing = false
                setupData()
            }
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is MainState.OnError -> toast(it.msg)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvWeather.run {
            val viewHolderMapper: (MainItem) -> ViewHolderType = {
                when (it) {
                    is MainItem.Header -> ItemHolderType.HeaderHolder
                    MainItem.Divider -> ItemHolderType.DividerHolder
                    is MainItem.Weather -> ItemHolderType.WeatherHolder
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<MainItem>() {
                override fun areItemsTheSame(
                    oldItem: MainItem,
                    newItem: MainItem
                ) = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: MainItem,
                    newItem: MainItem
                ) = oldItem == newItem
            }

            adapter = BaseMultiViewAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = ItemHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.fetch()
        }
    }

    enum class ItemHolderType(
        override val layoutResourceId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        HeaderHolder(
            layoutResourceId = R.layout.item_header,
            bindingItemId = BR.item
        ),
        DividerHolder(
            layoutResourceId = R.layout.item_divider,
            bindingItemId = BR.item
        ),
        WeatherHolder(
            layoutResourceId = R.layout.item_weather,
            bindingItemId = BR.item
        )
    }
}
