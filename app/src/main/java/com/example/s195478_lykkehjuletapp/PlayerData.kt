package com.example.s195478_lykkehjuletapp

data class PlayerData(var score: Int, var life: Int){
    fun removeLife(){
        if (life <= 0){
            life = 0
        } else {
            life--
        }
    }

    fun addLife(){
        if (life < 0){
            life = 0
        } else {
            life++
        }
    }

    fun bankruptPlayer(){
        score = 0
    }
}
