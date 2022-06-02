package com.currencyconverter.views.activities

import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.currencyconverter.databinding.ActivityHomeBinding
import com.currencyconverter.utils.*
import com.currencyconverter.viewmodels.HomeViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null
    private val homeViewModel by viewModel<HomeViewModel>()
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getCurrencies()

        bindViews()

    }

    private fun bindViews() {
        binding?.apply {
            etCurrencyAmount.doOnTextChanged { text, _, _, _ ->
                handler?.removeCallbacksAndMessages(null)
                handler = delay {
                    println("calling api now: $text")
                }
            }

        }
    }

    private fun getCurrencies() {

        homeViewModel.getCurrenciesList()

        homeViewModel.currenciesList.observe(this) { response ->
            response?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.apply {
                            val jsonObject = JSONObject(this)
                            if (jsonObject.getBoolean("success")) {

                                val spinnerData: MutableList<String> = ArrayList()

                                val currencies = jsonObject.getJSONObject("currencies")

                                val keys = currencies.keys().asSequence().toList()
                                spinnerData.addAll(keys)

                                val adapter = ArrayAdapter(this@HomeActivity,
                                    android.R.layout.simple_spinner_item, spinnerData)

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding?.spCurrencies?.adapter = adapter


                            } else {
                                println("TestLogs: success: failed")
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding?.pbLoading?.hide()
                        it.message?.let { msg -> toast(msg) }
                    }
                    Status.LOADING -> {
                        binding?.pbLoading?.show()
                    }
                }
            }
        }

    }
}