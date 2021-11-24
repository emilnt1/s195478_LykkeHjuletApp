package com.example.s195478_lykkehjuletapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.navigation.Navigation
import com.example.s195478_lykkehjuletapp.databinding.FragmentWordsBinding
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class WordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private var amount: Int = 0
//    private var player = PlayerData(0,5)
//    private var guessedLetters = mutableListOf<Char>()
//    private var checkFlag = 0
//    private val category = Category.values().random()
//    private val word = ListOfWords.generateWord(category)
//    private val hiddenWord = ListOfWords.genereateHiddenWord(word)
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

        binding.btnBack.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_wordsFragment_to_startFragment) }
        binding.spinnginWheel.setOnClickListener {
            if (!binding.btnGuess.isEnabled) {
                spinWheel(view)
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

    fun spinWheel(view: View) {

        animateSpinningWheel()

        val outcome = WheelOutcome.randomWheelSpin()

        //binding.wheelText.text = outcome.name

        if (viewModel.player.life <=0){
            loseGameScreen(view)
        } else if (viewModel.word.equals(viewModel.hiddenWord)){
            winGameScreen(view)
        }
        Thread.sleep(1500)
        when(outcome){
            Outcomes.VALUE -> displayAmount()
            Outcomes.EXTRATURN -> extraTurn()
            Outcomes.MISSTURN -> missTurn()
            Outcomes.BANKRUPT -> bankruptPlayer()
        }


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
        viewModel.guessedLetters.add(guessedLetter)
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
        for (i in viewModel.guessedLetters){

                tempWord = "$tempWord $i,"

        }

        binding.guessedLetters.text = tempWord
    }

    fun loseGameScreen(view: View){
        Navigation.findNavController(view).navigate(R.id.action_wordsFragment_to_loseGameFragment)
    }

    fun winGameScreen(view: View){
        Navigation.findNavController(view).navigate(R.id.action_wordsFragment_to_winGameFragment)
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




