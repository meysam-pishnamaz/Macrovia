package com.dante.macrovia.model

import com.dante.macrovia.utils.cmToFeetAndInches
import com.dante.macrovia.utils.kgToLbs
import java.util.Date

data class User(
    val firstName: String,
    val lastName: String,
    val birthDay: Date,
    val heightCm: Double,
    val weightKg: Double,
    val gender: Gender,
    val activityLevel: ActivityLevel,
    val goal: Goal,
    val units: Units

) {
    val fullName = "$firstName $lastName"

    val isImperial: Boolean
        get() = units == Units.Imperial

    val isMetric: Boolean
        get() = units == Units.Metric

    val weightInPounds: Double = kgToLbs(weightKg)

    val heightInFeetAndInches: Pair<Int, Int> = cmToFeetAndInches(heightCm)
}