package com.currencyconverter.views.activities

import android.os.Bundle
import com.currencyconverter.databinding.ActivityHomeBinding
import com.currencyconverter.utils.Status
import com.currencyconverter.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        homeViewModel.getCurrenciesList()

        homeViewModel.response.observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    println("TestLogs: success: ${it.data?.string()}")
                }
                Status.ERROR -> {
                    println("TestLogs: error: ${it.message}")
                }
                Status.LOADING -> {
                    println("TestLogs: loading")
                }
            }
        }

    }
}