package com.example.morty.epoxy

import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.morty.R
import com.example.morty.databinding.ModelLoadingBinding

class LoadingEpoxyModel: ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {
    override fun ModelLoadingBinding.bind() {
    }
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}