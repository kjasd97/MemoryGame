package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.jinatonic.confetti.CommonConfetti
import com.ulyanenko.memorygame.R
import com.ulyanenko.memorygame.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var count: Int = 0
    private var time = 0
    var numPairsFound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val startTime = System.currentTimeMillis()

        val images = mutableListOf(
            R.drawable.ic_heart,
            R.drawable.ic_light,
            R.drawable.ic_smile
        )
        images.addAll(images)
        images.shuffle()

        buttons = listOf(
            binding.imageButton1, binding.imageButton2, binding.imageButton3,
            binding.imageButton4, binding.imageButton5,
            binding.imageButton6
        )

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        binding.countOfAttempt.text = String.format(getString(R.string.attempt), returnCont())
        binding.time.text = String.format(getString(R.string.time), time)


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                it.animate().apply {
                    duration = 1000
                    rotationYBy(360f)
                }.start()

                count++
                binding.countOfAttempt.text =
                    String.format(getString(R.string.attempt), returnCont())
                updateModels(index)
                updateViews()

                if (haveWonGame()) {
                    val endTime = System.currentTimeMillis()
                    binding.time.text = String.format(getString(R.string.time), formatTime(endTime-startTime))

                    Toast.makeText(this, "You are winner", Toast.LENGTH_SHORT).show()
                    CommonConfetti.rainingConfetti(
                        binding.root,
                        intArrayOf(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    ).oneShot()
                }
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


    private fun updateViews() {
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

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            numPairsFound++
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == 3
    }

    fun returnCont(): Int {
        return count / 2
    }

    private fun formatTime(millis: Long): String {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

    }
}