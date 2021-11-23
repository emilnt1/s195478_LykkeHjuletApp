package com.example.s195478_lykkehjuletapp

enum class Category(val words: List<String>){

    ANIMALS(listOf<String>("dog", "cat")),
    FOOD(listOf<String>("carrot", "tomato")),
    TREES(listOf<String>("birch", "redwood"))
}

object ListOfWords{

    public fun generateWord(category: Category): String {
        val index: Int = (category.words.indices).random()

        return category.words[index]
    }

}
