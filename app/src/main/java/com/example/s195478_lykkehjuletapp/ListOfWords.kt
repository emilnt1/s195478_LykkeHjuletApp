package com.example.s195478_lykkehjuletapp

import androidx.compose.ui.text.toUpperCase
import java.util.*

enum class Category(val words: List<String>){

    ANIMALS(listOf<String>("dog", "cat")),
    FOOD(listOf<String>("carrot", "tomato")),
    TREES(listOf<String>("birch", "redwood"))
}

object ListOfWords{

    public fun generateWord(category: Category): String {
        val index: Int = (category.words.indices).random()

        return category.words[index].uppercase(Locale.getDefault())
    }

    public fun genereateHiddenWord(word: String): String{
        val wordChars = word.toCharArray()
        for (i in wordChars.indices){
            wordChars[i] = '_'
        }
        return String(wordChars)
    }

}
