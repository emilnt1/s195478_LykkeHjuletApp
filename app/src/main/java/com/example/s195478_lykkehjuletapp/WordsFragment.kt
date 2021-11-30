package com.example.s195478_lykkehjuletapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import com.example.s195478_lykkehjuletapp.databinding.FragmentWordsBinding
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var wordAdapter: WordAdapter //= WordAdapter(hiddenWord)
    private lateinit var layoutManager: FlexboxLayoutManager
    private lateinit var viewModel:WordsFragmentViewModel //= ViewModelProvider(this).get(WordsFragmentViewModel::class.java)
    private var checkFlag = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordsBinding.inflate(inflater,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(WordsFragmentViewModel::class.java)
        // creating the recycler view for the hidden word boxes
        wordAdapter = WordAdapter(viewModel.hiddenWord)
        binding.boxesOfWords.adapter = wordAdapter
        layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.boxesOfWords.layoutManager = layoutManager

        binding.textInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                return@OnKeyListener true
            }
            false
        })


        binding.btnBack.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_wordsFragment_to_startFragment) }
        binding.spinnginWheel.setOnClickListener {
            if (!binding.btnGuess.isEnabled) {
                spinWheel()
            }
        }


        val wordChars = viewModel.word.toCharArray()
        for (i in wordChars.indices){
            wordChars[i] = '_'
        }
        viewModel.hiddenWord = String(wordChars)

        binding.categoryText.text = viewModel.category.name
        binding.wordText.text = viewModel.word
        binding.btnGuess.setOnClickListener {
            if (binding.textInput.text.toString().isNotEmpty()) {
                val letter = binding.textInput.text.toString().first()
                Log.d(TAG, "onCreateView: $letter")
                checkLetter(letter, viewModel.word)
            }
        }

        return view
    }

    fun spinWheel() {
        animateSpinningWheel()
        val outcome = WheelOutcome.randomWheelSpin()

        //binding.wheelText.text = outcome.name
        //loseGameScreen()

        when (outcome) {
            Outcomes.VALUE -> displayAmount()
            Outcomes.EXTRATURN -> extraTurn()
            Outcomes.MISSTURN -> missTurn()
            Outcomes.BANKRUPT -> bankruptPlayer()
        }

        isGameOver()
        //Toast.makeText(context,"The wheel was spun",Toast.LENGTH_SHORT).show()


    }

    fun displayAmount(){
        viewModel.amount = 100 * (10..20).random()
        binding.wheelText.text = viewModel.amount.toString()
        enableEditText(binding.textInput)
        binding.btnGuess.isEnabled = true

    }

    fun extraTurn(){
        viewModel.player.addLife()
        binding.lifeText.text = "Life: " + viewModel.player.life.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Extra Turn"
    }

    fun missTurn(){
        viewModel.player.removeLife()
        binding.lifeText.text = "Life: " +  viewModel.player.life.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Miss Turn"
    }

    fun bankruptPlayer(){
        viewModel.player.bankruptPlayer()
        binding.pointText.text = "Score: " + viewModel.player.score.toString()
        disableEditText(binding.textInput)
        binding.btnGuess.isEnabled = false
        binding.wheelText.text = "Bankrupt"
    }

    fun checkLetter(letter: Char, word: String){
        val guessedLetter = letter.uppercaseChar()

        for (i in word.indices){
            if (guessedLetter.equals("")){
                break
            }
            Log.d(TAG, "checkLetter: " + (guessedLetter == word[i]))
            if (guessedLetter == word[i] && !viewModel.guessedLetters.contains(guessedLetter)){
                viewModel.player.score += viewModel.amount
                Log.d(TAG, "checkLetter: playerscore = " + viewModel.player.score )
                binding.pointText.text = "Score: " + viewModel.player.score.toString()

                viewModel.hiddenWord = changeCharInString(i, guessedLetter, viewModel.hiddenWord)
                Log.d(TAG, "checkLetter: hidden word is = ${viewModel.hiddenWord}")
                checkFlag++
                wordAdapter.setData(viewModel.hiddenWord)
                wordAdapter.notifyItemChanged(i)

            }
        }
        if (checkFlag <= 0 && !viewModel.guessedLetters.contains(guessedLetter)){
            viewModel.player.removeLife()
            binding.lifeText.text = "Life: " + viewModel.player.life.toString()
            disableEditText(binding.textInput)
            binding.wheelText.text = "Spin the wheel again"
            binding.btnGuess.isEnabled = false

        }

        checkFlag = 0
        if (!viewModel.guessedLetters.contains(guessedLetter)){
            viewModel.guessedLetters.add(guessedLetter)
        }
        binding.textInput.setText("")
        writeGuessedLetter()
        isGameOver()

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
        for (i in viewModel.guessedLetters){

                tempWord = "$tempWord $i,"

        }

        binding.guessedLetters.text = tempWord
    }

    fun isGameOver(){
        if (viewModel.player.life <=0){
            loseGameScreen()
        } else if (viewModel.word.equals(viewModel.hiddenWord)){
            winGameScreen()
        }
    }

    fun loseGameScreen(){
        val action = WordsFragmentDirections.actionWordsFragmentToGameEndDialog(false)
        binding.root.findNavController().navigate(action)
    }

    fun winGameScreen(){
        val action = WordsFragmentDirections.actionWordsFragmentToGameEndDialog(true)
        binding.root.findNavController().navigate(action)
    }

    fun animateSpinningWheel(){
        val rotateAnimation = AnimationUtils.loadAnimation(context,R.anim.rotate_wheel)
        binding.spinnginWheel.startAnimation(rotateAnimation)

    }


}

private fun changeCharInString(i: Int, letter: Char, hiddenWord: String): String {
    val chars = hiddenWord.toCharArray()
    chars[i] = letter
    return String(chars)
}






