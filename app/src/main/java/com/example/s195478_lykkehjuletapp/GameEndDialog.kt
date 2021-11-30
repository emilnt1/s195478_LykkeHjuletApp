package com.example.s195478_lykkehjuletapp

import android.app.Activity
import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView

import com.example.s195478_lykkehjuletapp.databinding.FragmentCustomDialogBinding
import com.example.s195478_lykkehjuletapp.databinding.FragmentWordsBinding
import java.io.InputStream

class GameEndDialog(): DialogFragment() {

    private var _binding: FragmentCustomDialogBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: GameEndDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomDialogBinding.inflate(inflater,container,false)
        val view = binding.root


        val endGameState = args.endGameState
        if (endGameState){
            binding.endGameText.text = "You did great, you guessed the word!"
            val animationView:LottieAnimationView = binding.animationView
            animationView.setAnimation(R.raw.party_emoji_anim)
            animationView.playAnimation()

        } else{
            binding.endGameText.text = "You ran out of lives. Boo Hoo..."
            val animationView:LottieAnimationView = binding.animationView
            animationView.setAnimation(R.raw.sad_emoji_anim)
            animationView.scale = 0.6f
            animationView.playAnimation()
        }

        binding.btnToStart.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_gameEndDialog_to_startFragment)
        }

        return view
    }




}