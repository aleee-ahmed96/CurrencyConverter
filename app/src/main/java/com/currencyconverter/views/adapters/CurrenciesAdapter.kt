package com.currencyconverter.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.currencyconverter.databinding.LayoutCurrenciesBinding
import com.currencyconverter.models.CurrenciesModel
import com.currencyconverter.utils.CurrencyDiffUtils

class CurrenciesAdapter(
    private val currenciesData: MutableList<CurrenciesModel>
) : RecyclerView.Adapter<CurrenciesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        return CurrenciesViewHolder(
            LayoutCurrenciesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        val item = currenciesData[holder.adapterPosition]
        holder.bindData(item)
    }

    override fun getItemCount(): Int = currenciesData.size

    fun updateList(newCurrenciesData: ArrayList<CurrenciesModel>) {
        val diffCallback = CurrencyDiffUtils(currenciesData, newCurrenciesData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        currenciesData.clear()
        currenciesData.addAll(newCurrenciesData)
        diffResult.dispatchUpdatesTo(this)
    }

}