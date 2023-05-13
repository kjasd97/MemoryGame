package com.ulyanenko.memorygame.presentation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {


    private val _countOfAttempts = MutableStateFlow(0)
    val countOfAttempts = _countOfAttempts.asStateFlow()

    private val _resultTime = MutableStateFlow("0")
    val resultTime = _resultTime.asStateFlow()

    private val _cards = MutableStateFlow(mutableListOf<MemoryCard>())
    val cards = _cards.asStateFlow()

    private val _isMatched = MutableStateFlow(false)
    val isMatched = _isMatched.asStateFlow()

    private val _hasWon = MutableStateFlow(false)
    val hasWon = _hasWon.asStateFlow()


    private var indexOfSingleSelectedCard: Int? = null

    private var numPairsFound = 0


    fun createCardsFromImages(images: List<Int>) {
        val cards = mutableListOf<MemoryCard>()
        cards.addAll(images.map { MemoryCard(it) })
        _cards.value = cards
    }

    fun returnCount(countOfClicks: Int) {
        _countOfAttempts.value = countOfClicks / 2
    }

    fun formatTime(millis: Long) {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        _resultTime.value = String.format("%02d:%02d", minutes, leftSeconds)
    }

    fun updateModels(position: Int) {
        val cards = cards.value
        val card = cards[position]
        if (card.isFaceUp) {
            return
        }
        if (indexOfSingleSelectedCard == null) {
            restoreCards(cards)
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position, cards)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        _cards.value = cards
    }

    private fun restoreCards(cards:List<MemoryCard>) {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int, cards: List<MemoryCard>) {
        if (cards[position1].identifier == cards[position2].identifier) {
            _isMatched.value = true
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            numPairsFound++
        }
    }

    fun hasWonGame() {
        if (numPairsFound == 3) {
            _hasWon.value = true
        }
    }

    companion object {

        private const val NUMBER_OF_CARDS = 6

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

    }

}