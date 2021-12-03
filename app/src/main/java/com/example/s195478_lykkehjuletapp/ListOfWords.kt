package com.example.s195478_lykkehjuletapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import java.util.*

//Inspiration from https://www.baeldung.com/kotlin/enum
enum class Category(val words: List<String>){

    ANIMALS(listOf<String>("dog", "cat", "dingo", "kangaroo")),
    FOOD(listOf<String>("carrot", "tomato", "lasagne", "ravioli")),
    TREES(listOf<String>("birch", "redwood", "maple", "oak", "baobab")),
    COUNTRIES(listOf<String>("denmark", "america", "russia", "bulgaria"))

}

object ListOfWords{

    public fun generateWord(category: Category): String {
        val index: Int = (category.words.indices).random()
        Log.d(TAG, "generateWord: " + category.words.random())

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
