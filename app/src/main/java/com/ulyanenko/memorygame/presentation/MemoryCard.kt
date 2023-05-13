package com.ulyanenko.memorygame.presentation

data class MemoryCard(
    var identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)