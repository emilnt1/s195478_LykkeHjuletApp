package com.example.s195478_lykkehjuletapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.s195478_lykkehjuletapp.databinding.FragmentStartBinding
import com.example.s195478_lykkehjuletapp.databinding.FragmentWordsBinding



class wordsFragment : Fragment() {

    private var _binding: FragmentWordsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordsBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.backButton.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigateToStartFragment) }
        binding.spinnginWheel.setOnClickListener { spinWheel() }


        return view
    }

    fun spinWheel() {
        Toast.makeText(context,"The wheel was spun",Toast.LENGTH_SHORT).show()
    }

    fun animateSpinningWheel(){

    }


}


