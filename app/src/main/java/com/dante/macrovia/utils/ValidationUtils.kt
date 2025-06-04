package com.dante.macrovia.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun validateFirstName(firstName: String): String? {
    return when {
        firstName.isBlank() -> "First name is required"
        firstName.length < 3 -> "First name must be at least 3 characters"
        firstName.length > 50 -> "First name cannot be longer than 50 characters"
        firstName.any { !it.isLetter() && it != ' ' } -> "First name can only contain letters and spaces"
        !firstName.first().isLetter() || !firstName.last().isLetter() ->
            "First name must start and end with a letter"
        else -> null
    }
}

fun validateLastName(lastName: String): String? {
    val validChars = { c: Char -> c.isLetter() || c == ' ' || c == '\'' || c == '-' }

    return when {
        lastName.isBlank() -> "Last name is required"
        lastName.length < 2 -> "Last name must be at least 2 characters"
        lastName.length > 50 -> "Last name cannot be longer than 50 characters"
        lastName.any { !validChars(it) } -> "Last name can only contain letters, spaces, hyphens, or apostrophes"
        !lastName.first().isLetter() || !lastName.last().isLetter() ->
            "Last name must start and end with a letter"
        else -> null
    }
}

fun validateHeight(
    imperial: Boolean,
    heightFeet: String,
    heightInches: String,
    heightCm: String
): String? {
    return if (imperial) {
        when {
            heightFeet.isBlank() -> "Height (feet) is required"
            heightFeet.toIntOrNull() !in 1..8 -> "Height (feet) must be between 1 and 8"
            heightInches.isBlank() -> "Height (inches) is required"
            heightInches.toIntOrNull() !in 0..11 -> "Height (inches) must be between 0 and 11"
            else -> null
        }
    } else {
        when {
            heightCm.isBlank() -> "Height is required"
            heightCm.toIntOrNull() !in 40..272 -> "Height must be between 40 and 272"
            else -> null
        }
    }
}

fun validateWeight(
    imperial: Boolean,
    weightLbs: String,
    weightKg: String
): String? {
    return if (imperial) {
        when {
            weightLbs.isBlank() -> "Weight (lbs) is required"
            weightLbs.toIntOrNull() !in 30..1400 -> "Weight must be between 30 and 1400"
            else -> null
        }
    } else {
        when {
            weightKg.isBlank() -> "Weight (kg) is required"
            weightKg.toIntOrNull() !in 14..635 -> "Weight must be between 14 and 635"
            else -> null
        }
    }
}

fun validateBirthday(birthday: String): String? {
    if (birthday.isBlank()) return "Birthday is required"

    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            isLenient = false
        }
        val birthDate = formatter.parse(birthday) ?: return "Invalid date format"

        val age = getAge(birthDate, Calendar.getInstance().time)

        when {
            age !in 13..100 -> "Age must be between 13 and 100 years"
            else -> null
        }
    } catch (e: Exception) {
        "Invalid date format"
    }
}

private fun getAge(birthDate: Date, currentDate: Date): Int {
    val birth = Calendar.getInstance().apply { time = birthDate }
    val current = Calendar.getInstance().apply { time = currentDate }

    var age = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
    if (current.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}