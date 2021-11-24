package com.example.s195478_lykkehjuletapp

import androidx.lifecycle.ViewModel

class WordsFragmentViewModel: ViewModel() {
    var amount: Int = 0
    var player = PlayerData(0,5)
    var guessedLetters = mutableListOf<Char>()
    var checkFlag = 0
    val category = Category.values().random()
    val word = ListOfWords.generateWord(category)
    var hiddenWord = ListOfWords.genereateHiddenWord(word)
}