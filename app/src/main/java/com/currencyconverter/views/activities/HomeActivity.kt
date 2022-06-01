package com.currencyconverter.views.activities

import android.os.Bundle
import com.currencyconverter.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)



    }
}