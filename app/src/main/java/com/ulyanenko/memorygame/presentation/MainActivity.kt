package com.ulyanenko.memorygame.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ulyanenko.memorygame.R
import com.ulyanenko.memorygame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
   ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonGame.setOnClickListener {
        val intent = GameActivity.newIntent(this)
            startActivity(intent)
        }

        binding.ButtonGetJson.setOnClickListener {
            val intent = JsonActivity2.newIntent(this)
            startActivity(intent)
        }

        binding.ButtonExit.setOnClickListener {
            finish()
        }
    }
}