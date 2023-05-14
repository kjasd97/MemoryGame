package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ulyanenko.memorygame.MemoryGameApp
import com.ulyanenko.memorygame.databinding.ActivityJson2Binding
import javax.inject.Inject

class JsonActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityJson2Binding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
     ViewModelProvider(this, viewModelFactory).get(JsonViewModel::class.java)
    }

    private val component by lazy {
        (application as MemoryGameApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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
            return Intent(context, JsonActivity::class.java)
        }
    }
}