package com.dante.macrovia.utils

import kotlin.math.floor

fun cmToFeetAndInches(cm: Double): Pair<Int, Int> {
    val totalInches = cm / 2.54
    val feet = floor(totalInches / 12).toInt()
    val inches = floor(totalInches % 12).toInt()
    return Pair(feet, inches)
}

fun feetAndInchesToCm(feet: Int, inches: Int): Double {
    return (feet * 12 + inches) * 2.54
}

fun kgToLbs(kg: Double): Double {
    return kg * 2.2046226218
}

fun lbsToKg(lbs: Double): Double {
    return lbs / 2.2046226218
}