package com.example.s195478_lykkehjuletapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.s195478_lykkehjuletapp.databinding.FragmentLoseGameBinding
import com.example.s195478_lykkehjuletapp.databinding.FragmentStartBinding


class LoseGameFragment : Fragment() {
    private var _binding: FragmentLoseGameBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoseGameBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnLoseScreen.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_loseGameFragment_to_startFragment)
        }
        return view
    }


}