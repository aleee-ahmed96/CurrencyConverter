package com.currencyconverter.utils

import androidx.recyclerview.widget.DiffUtil
import com.currencyconverter.models.CurrenciesModel

class CurrencyDiffUtils(
    private val oldList: List<CurrenciesModel>,
    private val newList: List<CurrenciesModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].currencyValue == newList[newItemPosition].currencyValue

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}