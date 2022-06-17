package com.currencyconverter.views.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.currencyconverter.database.entities.CurrenciesChangeEntity
import com.currencyconverter.database.entities.CurrenciesListEntity
import com.currencyconverter.databinding.ActivityHomeBinding
import com.currencyconverter.models.CurrenciesModel
import com.currencyconverter.utils.*
import com.currencyconverter.viewmodels.HomeViewModel
import com.currencyconverter.views.adapters.CurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null

    private val homeViewModel: HomeViewModel by viewModels()

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
        binding?.apply {

            etCurrencyAmount.doOnTextChanged { text, _, _, _ ->
                handler?.removeCallbacksAndMessages(null)
                handler = delay {
                    if (spCurrencies.selectedItem != null && text.isNullOrBlank().not()) {
                        getCurrencyChange()
                    }
                }
            }

            spCurrencies.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (spCurrencies.selectedItem != null && etCurrencyAmount.text.isNullOrBlank().not()) {
                        getCurrencyChange()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            }

            rvCurrenciesList.layoutManager = GridLayoutManager(this@HomeActivity, 3)
            rvCurrenciesList.adapter = currenciesAdapter

        }
    }

    private fun getCurrencies() {

        if (shouldCallApi(homeViewModel.getListApiTime())) {
            if (isInternetConnected()) homeViewModel.getCurrenciesList()
            else homeViewModel.getCurrencyListFromDB()
        }
        else {
            homeViewModel.getCurrencyListFromDB()
        }

        homeViewModel.currenciesListRemote.observe(this) { response ->
            response?.let {
                when(it.status) {
                    Status.SUCCESS -> {

                        binding?.pbLoading?.hide()

                        it.data?.apply {

                            homeViewModel.setListApiTime()
                            val jsonObject = JSONObject(this)

                            if (jsonObject.getBoolean("success")) {

                                if (jsonObject.has("currencies")) {

                                    val spinnerData = arrayListOf<String>()
                                    val dbData = arrayListOf<CurrenciesListEntity>()

                                    val currencies = jsonObject.getJSONObject("currencies")

                                    val keys = currencies.keys().asSequence().toList()


                                    keys.forEach { item -> dbData.add(CurrenciesListEntity(item)) }

                                    spinnerData.addAll(keys)

                                    val adapter = ArrayAdapter(this@HomeActivity,
                                        android.R.layout.simple_spinner_item, spinnerData)

                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    binding?.spCurrencies?.adapter = adapter

                                    homeViewModel.addCurrencyListToDB(dbData)

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

        homeViewModel.currenciesListLocal.observe(this) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.let { list ->
                            val spinnerData: MutableList<String> = ArrayList()

                            list.forEach { item -> spinnerData.add(item.currency) }

                            val adapter = ArrayAdapter(this@HomeActivity,
                                android.R.layout.simple_spinner_item, spinnerData)

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding?.spCurrencies?.adapter = adapter


                        } ?: run {
                            if (isInternetConnected()) homeViewModel.getCurrenciesList()
                            else toast("No internet connection")
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

    private fun ActivityHomeBinding.getCurrencyChange() {

        if (shouldCallApi(homeViewModel.getChangeApiTime())) {
            if (isInternetConnected()) {
                homeViewModel.getCurrencyChange(spCurrencies.selectedItem.toString())
            }
            else {
                homeViewModel.getCurrencyChangeFromDB(spCurrencies.selectedItem.toString())
            }
        }
        else {
            homeViewModel.getCurrencyChangeFromDB(spCurrencies.selectedItem.toString())
        }
    }

    private fun observeCurrencyChange() {

        homeViewModel.currencyChangeLocal.observe(this) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.let { list ->

                            currenciesData.clear()

                            list.forEach {item->

                                currenciesData.add(
                                    CurrenciesModel(
                                        item.currencyName.removePrefix(binding?.spCurrencies?.selectedItem.toString()),
                                        item.currencyRate *
                                                (binding?.etCurrencyAmount?.textString()?.toDouble() ?: 1.0)
                                    )
                                )
                            }
                            currenciesAdapter.updateList(currenciesData)

                        } ?: run {
                            if (isInternetConnected()) {
                                homeViewModel.getCurrencyChange(binding?.spCurrencies?.selectedItem.toString())
                            }
                            else toast("No internet connection")
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

        homeViewModel.currencyChangeRemote.observe(this) { response ->
            response?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        binding?.pbLoading?.hide()
                        it.data?.apply {
                            val jsonObject = JSONObject(this)
                            if (jsonObject.getBoolean("success")) {

                                currenciesData.clear()

                                if (jsonObject.has("quotes")) {

                                    homeViewModel.setChangeApiTime()

                                    val changeEntityList = arrayListOf<CurrenciesChangeEntity>()
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
                                            changeEntityList.add(
                                                CurrenciesChangeEntity(
                                                    currencyName = item, currencyRate = rate))
                                        }
                                    }

                                    homeViewModel.addCurrencyChangeToDB(
                                        binding?.spCurrencies?.selectedItem.toString(),
                                        changeEntityList
                                    )

                                }

                                currenciesAdapter.updateList(currenciesData)

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

}