package com.currencyconverter.views.adapters

import androidx.recyclerview.widget.RecyclerView
import com.currencyconverter.databinding.LayoutCurrenciesBinding
import com.currencyconverter.models.CurrenciesModel
import java.util.*

class CurrenciesViewHolder(
    private val binding: LayoutCurrenciesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(currenciesModel: CurrenciesModel) {
        binding.apply {
            tvCurrency.text = currenciesModel.currencyName
            tvAmount.text =
                String.format(Locale.getDefault(), "%.02f", currenciesModel.currencyValue)

        }
    }

}
