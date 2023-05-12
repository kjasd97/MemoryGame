package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    var numPairsFound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
            binding.imageButton6,
        )

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        binding.countOfAttempt.text = String.format(getString(R.string.attempt), returnCont())

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                count++
                binding.countOfAttempt.text = String.format(getString(R.string.attempt), returnCont())
                updateModels(index)
                updateViews()
                if(haveWonGame()){
                    Toast.makeText(this, "You are winner", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.toMenu.setOnClickListener {
            finish()
        }

        binding.restart.setOnClickListener {
            images.shuffle()
            cards = buttons.indices.map { index ->
                MemoryCard(images[index])
            }
           buttons.forEach {
               it.alpha = 1f
               it.setImageResource(R.drawable.ic_code)
           }
            numPairsFound=0
            count=0

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

    fun returnCont():Int{
        return count/2
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

}