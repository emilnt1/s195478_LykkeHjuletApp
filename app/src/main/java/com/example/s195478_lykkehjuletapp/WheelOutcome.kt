package com.example.s195478_lykkehjuletapp

enum class Outcomes{
    VALUE, EXTRATURN, MISSTURN, BANKRUPT
}

object WheelOutcome {

    val listOfOutcomes = listOf(
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.VALUE,
        Outcomes.EXTRATURN,
        Outcomes.MISSTURN,
        Outcomes.BANKRUPT,
        Outcomes.BANKRUPT
    )

    public fun randomWheelSpin():  Outcomes{
        return listOfOutcomes.random()
    }

}