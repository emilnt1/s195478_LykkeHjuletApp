package com.example.s195478_lykkehjuletapp

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// inspiration taken from https://developer.android.com/guide/topics/ui/layout/recyclerview
class WordAdapter (private var dataSet: String) : RecyclerView.Adapter<WordAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView

        init {
            textView = view.findViewById(R.id.letterInWord)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wordbox_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].toString()
        Log.d(TAG, "onBindViewHolder: dataset char = " + dataSet[position].toString())
    }

    override fun getItemCount() = dataSet.length

    fun setData(newHiddenWord: String){
        dataSet = newHiddenWord
    }

}