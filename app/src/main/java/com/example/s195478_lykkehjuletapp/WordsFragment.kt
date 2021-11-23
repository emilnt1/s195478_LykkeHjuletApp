package com.example.s195478_lykkehjuletapp

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.s195478_lykkehjuletapp.databinding.FragmentStartBinding
import com.example.s195478_lykkehjuletapp.databinding.FragmentWordsBinding
import android.widget.EditText






class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var amount: Int = 0
    private var player = PlayerData(0,5)
    private var guessedLetters = mutableListOf<Char>()
    private var checkFlag = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordsBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.btnBack.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateToStartFragment) }
        binding.spinnginWheel.setOnClickListener {
            if (!binding.btnGuess.isEnabled) {
                spinWheel()
            }
        }

        val category = Category.values().random()
        val word = ListOfWords.generateWord(category)

        binding.categoryText.text = category.name
        binding.wordText.text = word
        binding.btnGuess.setOnClickListener {
            if (binding.textInput.text.toString().isNotEmpty()) {
                val letter = binding.textInput.text.toString().first()
                Log.d(TAG, "onCreateView: $letter")
                checkLetter(letter, word)
            }
        }




        return view
    }

    fun spinWheel() {

        val outcome = WheelOutcome.randomWheelSpin()

        //binding.wheelText.text = outcome.name

        when(outcome){
            Outcomes.VALUE -> displayAmount()
            Outcomes.EXTRATURN -> extraTurn()
            Outcomes.MISSTURN -> missTurn()
            Outcomes.BANKRUPT -> bankruptPlayer()
        }

        if (player.life <= 0){
            endGameScreen()
        }
        //Toast.makeText(context,"The wheel was spun",Toast.LENGTH_SHORT).show()

    }

    fun displayAmount(){
        amount = 100 * (10..20).random()
        binding.wheelText.text = amount.toString()
        enableEditText(binding.textInput)
        binding.btnGuess.isEnabled = true

    }

    fun extraTurn(){
        player.addLife()
        binding.lifeText.text = player.life.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Extra Turn"
    }

    fun missTurn(){
        player.removeLife()
        binding.lifeText.text = player.life.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Miss Turn"
    }

    fun bankruptPlayer(){
        player.bankruptPlayer()
        binding.pointText.text = player.score.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Bankrupt"
    }

    fun checkLetter(letter: Char, word: String){
        val guessedLetter = letter.lowercaseChar()


        for (i in word.indices){
            if (letter.equals("")){
                break
            }
            Log.d(TAG, "checkLetter: " + (letter == word[i]))
            if (letter == word[i] && !guessedLetters.contains(letter)){
                player.score += amount
                Log.d(TAG, "checkLetter: " + player.score)
                binding.pointText.text = player.score.toString()
                checkFlag++

            }
        }
        if (checkFlag <= 0 && !guessedLetters.contains(letter)){
            player.removeLife()
            binding.lifeText.text = player.life.toString()
            disableEditText(binding.textInput)
            binding.wheelText.text = "Spin the wheel again"
            binding.btnGuess.isEnabled = false
        }
        guessedLetters.add(letter)
        checkFlag = 0
        binding.textInput.setText("")
        writeGuessedLetter()

    }

    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
//        editText.isEnabled = false
//        editText.isCursorVisible = false
//        editText.keyListener = null
//        editText.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun enableEditText(editText: EditText) {
        editText.isFocusableInTouchMode = true
        editText.isFocusable = true
//        editText.isEnabled = true
//        editText.isCursorVisible = true
//        editText.keyListener
//        editText.setBackgroundColor(Color.red(2))
    }

    fun writeGuessedLetter(){
        var tempWord = "Guessed letters: \n"
        for (i in guessedLetters){

                tempWord = "$tempWord $i,"

        }

        binding.guessedLetters.text = tempWord
    }

    fun endGameScreen(){

    }

    fun animateSpinningWheel(){

    }


}


