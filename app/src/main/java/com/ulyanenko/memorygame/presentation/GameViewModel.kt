package com.ulyanenko.memorygame.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ulyanenko.memorygame.domain.MemoryCard
import javax.inject.Inject

class GameViewModel @Inject constructor() : ViewModel() {

    private val _countOfAttempts = MutableLiveData(0)
    val countOfAttempts: LiveData<Int>
        get() = _countOfAttempts

    private val _resultTime = MutableLiveData("0")
    val resultTime: LiveData<String>
        get() = _resultTime

    private val _cards = MutableLiveData<List<MemoryCard>>()
    val cards: LiveData<List<MemoryCard>>
        get() = _cards

    private val _isMatched = MutableLiveData(false)
    val isMatched: LiveData<Boolean>
        get() = _isMatched

    private val _hasWon = MutableLiveData(false)
    val hasWon: LiveData<Boolean>
        get() = _hasWon


    private var indexOfSingleSelectedCard: Int? = null

    private var numPairsFound = 0

    private var countOfClicks = 0


    fun createCardsFromImages(images: List<Int>) {
        val cards = mutableListOf<MemoryCard>()
        cards.addAll(images.map { MemoryCard(it) })
        _cards.value = cards
    }

    fun returnCount() {
        countOfClicks++
        _countOfAttempts.value = countOfClicks / 2
    }

    fun formatTime(millis: Long) {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        _resultTime.value = String.format("%02d:%02d", minutes, leftSeconds)
    }

    fun updateModels(position: Int) {
        val cards = cards.value?: listOf()
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

    fun restartGame(images: List<Int>){
        numPairsFound = 0
        indexOfSingleSelectedCard =0
        _resultTime.value = "0"
        _countOfAttempts.value = 0
        countOfClicks = 0
        _cards.value = images.map { MemoryCard(it) }
    }

    private fun restoreCards(cards: List<MemoryCard>) {
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

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

    }

}