package com.ulyanenko.memorygame.domain

data class MemoryCard(
    var identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)