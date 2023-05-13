package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.jinatonic.confetti.CommonConfetti
import com.ulyanenko.memorygame.R
import com.ulyanenko.memorygame.databinding.ActivityGameBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    private lateinit var buttons: List<ImageButton>
    private var startTime = 0L
    private var count: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startTime = System.currentTimeMillis()

        observeViewModel()

        val images = mutableListOf(
            R.drawable.ic_heart,
            R.drawable.ic_light,
            R.drawable.ic_smile
        )
        images.addAll(images)
        images.shuffle()


        buttons = listOf(
            binding.imageButton1,
            binding.imageButton2,
            binding.imageButton3,
            binding.imageButton4,
            binding.imageButton5,
            binding.imageButton6
        )

        viewModel.createCardsFromImages(images)


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                it.animate().apply {
                    duration = 1000
                    rotationYBy(360f)
                }.start()

                viewModel.returnCount(++count)

                viewModel.updateModels(index)

                viewModel.hasWonGame()

            }
        }

        binding.toMenu.setOnClickListener {
            finish()
        }

        binding.restart.setOnClickListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.countOfAttempts.collect {
                        binding.countOfAttempt.text = String.format(getString(R.string.attempt), it)
                    }
                }
                launch {
                    viewModel.resultTime.collect {
                        binding.time.text = String.format(getString(R.string.time), it)
                    }
                }
                launch {
                    viewModel.cards.collect {
                        updateViews(it)
                    }
                }

                launch {
                    viewModel.isMatched.collect {
                        Toast.makeText(this@GameActivity, "Match found!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                launch {
                    viewModel.hasWon.collect {
                        if (it) {
                            val endTime = System.currentTimeMillis()
                            viewModel.formatTime(endTime - startTime)
                            Toast.makeText(this@GameActivity, "You are winner", Toast.LENGTH_SHORT).show()
                            CommonConfetti.rainingConfetti(
                                binding.root,
                                intArrayOf(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                            ).oneShot()
                        }
                    }
                }
            }
        }


    }

    private fun updateViews(cards: List<MemoryCard>) {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            if (card.isFaceUp) {
                button.setImageResource(card.identifier)
            } else {
                button.setImageResource(R.drawable.ic_code)
            }

        }
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }
}