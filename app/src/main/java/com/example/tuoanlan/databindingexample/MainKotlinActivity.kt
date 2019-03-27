package com.example.tuoanlan.databindingexample

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tuoanlan.databindingexample.databinding.ActivityKotlinBinding

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//       val bing:MainKotlinActivityBinding =DataBindingUtil.setContentView(this,R.layout.activity_kotlin)

        val binding : ActivityKotlinBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_kotlin)
        binding.name="zhang"
        binding.lastName="axes"

    }
}
