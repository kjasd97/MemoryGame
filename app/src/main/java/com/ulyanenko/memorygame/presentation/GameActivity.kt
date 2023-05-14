package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.jinatonic.confetti.CommonConfetti
import com.ulyanenko.memorygame.MemoryGameApp
import com.ulyanenko.memorygame.R
import com.ulyanenko.memorygame.databinding.ActivityGameBinding
import com.ulyanenko.memorygame.domain.MemoryCard
import javax.inject.Inject


class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

    private val component by lazy {
        (application as MemoryGameApp).component
    }

    private lateinit var buttons: List<ImageButton>
    private var startTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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

                with(viewModel) {
                    returnCount()
                    updateModels(index)
                    hasWonGame()
                }

            }
        }

        binding.toMenu.setOnClickListener {
            finish()
        }

        binding.restart.setOnClickListener {
            images.shuffle()
            viewModel.restartGame(images)
            buttons.forEach {
                it.alpha = 1F
            }
            startTime = System.currentTimeMillis()
        }
    }

    private fun observeViewModel() {

        viewModel.countOfAttempts.observe(this) {
            binding.countOfAttempt.text = String.format(getString(R.string.attempt), it)
        }

        viewModel.resultTime.observe(this) {
            binding.time.text = String.format(getString(R.string.time), it)
        }

        viewModel.cards.observe(this) {
            updateViews(it)
        }

        viewModel.isMatched.observe(this) {
            if (it) {
                Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.hasWon.observe(this) {
            if (it) {
                val endTime = System.currentTimeMillis()
                viewModel.formatTime(endTime - startTime)
                Toast.makeText(this, "You are winner", Toast.LENGTH_SHORT)
                    .show()
                CommonConfetti.rainingConfetti(
                    binding.root,
                    intArrayOf(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                ).oneShot()
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