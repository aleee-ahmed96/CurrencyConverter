package com.currencyconverter.views.activities

import android.os.Bundle
import com.currencyconverter.databinding.ActivityHomeBinding
import com.currencyconverter.utils.Status
import com.currencyconverter.viewmodels.HomeViewModel
import org.json.JSONObject
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
            if (it != null) {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.apply {
                            println("TestLogs: success: $this")
                            val jsonObject = JSONObject(this)
                            if (jsonObject.getBoolean("success")) {
                                val currencies = jsonObject.getJSONObject("currencies")
                                val keys = currencies.keys()
                                for (key in keys) {
                                    println("TestLogs: success: $key = ${currencies.getString(key)}")
                                }
                            } else {
                                println("TestLogs: success: failed")
                            }
                        }
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
}