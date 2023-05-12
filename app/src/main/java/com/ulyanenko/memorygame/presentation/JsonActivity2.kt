package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ulyanenko.memorygame.R
import com.ulyanenko.memorygame.databinding.ActivityJson2Binding

class JsonActivity2 : AppCompatActivity() {

    private val binding by lazy {
        ActivityJson2Binding.inflate(layoutInflater)
    }

    private lateinit var viewModel: JsonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(JsonViewModel::class.java)

        viewModel.response.observe (this){
            Log.d("MainTest", "$it")
            binding.country.text = it.country
            binding.city.text = it.city
            binding.timeZone.text = it.timezone
        }



        binding.buttonGetJson.setOnClickListener {
            viewModel.loadData()
        }
    }



    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JsonActivity2::class.java)
        }
    }
}