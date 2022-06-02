package com.currencyconverter.views.activities

import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.currencyconverter.database.SharePreferences
import com.currencyconverter.databinding.ActivityHomeBinding
import com.currencyconverter.models.CurrenciesModel
import com.currencyconverter.utils.*
import com.currencyconverter.viewmodels.HomeViewModel
import com.currencyconverter.views.adapters.CurrenciesAdapter
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null

    private val homeViewModel by viewModel<HomeViewModel>()
    private val sharePreferences by inject<SharePreferences>()

    private var handler: Handler? = null
    private val currenciesData = arrayListOf<CurrenciesModel>()
    private val currenciesAdapter = CurrenciesAdapter(currenciesData.toMutableList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        bindViews()

        getCurrencies()

        observeCurrencyChange()
    }

    private fun bindViews() {
        toast(getCurrentDate())
        binding?.apply {
            etCurrencyAmount.doOnTextChanged { _, _, _, _ ->
                handler?.removeCallbacksAndMessages(null)
                handler = delay {
                    if (isInternetConnected()) {
                        homeViewModel.getCurrencyChange(spCurrencies.selectedItem.toString())
                    }
                    else toast("No internet connection")
                }
            }

            rvCurrenciesList.layoutManager = GridLayoutManager(this@HomeActivity, 3)
            rvCurrenciesList.adapter = currenciesAdapter

        }
    }

    private fun getCurrencies() {

        if (shouldCallApi(sharePreferences.getListApiCallTime())) {
            if (isInternetConnected()) homeViewModel.getCurrenciesList()
            else toast("No internet connection")
        } else {

        }

        homeViewModel.currenciesList.observe(this) { response ->
            response?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.apply {
                            val jsonObject = JSONObject(this)
                            if (jsonObject.getBoolean("success")) {

                                if (jsonObject.has("currencies")) {

                                    val spinnerData: MutableList<String> = ArrayList()

                                    val currencies = jsonObject.getJSONObject("currencies")

                                    val keys = currencies.keys().asSequence().toList()

                                    spinnerData.addAll(keys)

                                    val adapter = ArrayAdapter(this@HomeActivity,
                                        android.R.layout.simple_spinner_item, spinnerData)

                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    binding?.spCurrencies?.adapter = adapter

                                }

                            }
                            else {
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


    private fun observeCurrencyChange() {
        homeViewModel.currencyChange.observe(this) { response ->
            response?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.apply {
                            val jsonObject = JSONObject(this)
                            if (jsonObject.getBoolean("success")) {
                                currenciesData.clear()

                                if (jsonObject.has("quotes")) {

                                    val quotes = jsonObject.getJSONObject("quotes")
                                    val quoteItems = quotes.keys()

                                    binding?.apply{
                                        quoteItems.forEach { item ->
                                            val rate = quotes.getJSONObject(item).getDouble("end_rate")
                                            currenciesData.add(
                                                CurrenciesModel(
                                                    item.removePrefix(spCurrencies.selectedItem.toString()),
                                                    rate * etCurrencyAmount.textString().toDouble()
                                                )
                                            )
                                        }
                                    }
                                }

                                currenciesAdapter.updateList(currenciesData)
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