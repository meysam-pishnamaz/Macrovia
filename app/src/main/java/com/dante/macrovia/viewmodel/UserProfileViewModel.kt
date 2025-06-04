package com.dante.macrovia.viewmodel

import androidx.lifecycle.ViewModel
import com.dante.macrovia.repository.SharedPrefsRepository
import com.dante.macrovia.model.ActivityLevel
import com.dante.macrovia.model.Gender
import com.dante.macrovia.model.Goal
import com.dante.macrovia.model.Units
import com.dante.macrovia.model.User
import com.dante.macrovia.utils.feetAndInchesToCm
import com.dante.macrovia.utils.lbsToKg
import com.dante.macrovia.utils.validateBirthday
import com.dante.macrovia.utils.validateFirstName
import com.dante.macrovia.utils.validateHeight
import com.dante.macrovia.utils.validateLastName
import com.dante.macrovia.utils.validateWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel  @Inject constructor(
    private val prefs: SharedPrefsRepository
): ViewModel() {

    private val _selectedUnit = MutableStateFlow(Units.Imperial)
    val selectedUnit: StateFlow<Units> = _selectedUnit

    private val _userFirstName = MutableStateFlow("")
    val userFirstName: StateFlow<String> = _userFirstName

    private val _userLastName = MutableStateFlow("")
    val userLastName: StateFlow<String> = _userLastName

    private val _birthday = MutableStateFlow("")
    val birthday: StateFlow<String> = _birthday

    private val _heightInFeet = MutableStateFlow("")
    val heightInFeet: StateFlow<String> = _heightInFeet

    private val _heightInInches = MutableStateFlow("")
    val heightInInches: StateFlow<String> = _heightInInches

    private val _heightInCm = MutableStateFlow("")
    val heightInCm: StateFlow<String> = _heightInCm

    private val _weightInPounds = MutableStateFlow("")
    val weightInPounds: StateFlow<String> = _weightInPounds

    private val _weightInKg = MutableStateFlow("")
    val weightInKg: StateFlow<String> = _weightInKg

    private val _selectedGender = MutableStateFlow(Gender.Male)
    val selectedGender: StateFlow<Gender> = _selectedGender

    private val _selectedGoal = MutableStateFlow(Goal.LoseWeight)
    val selectedGoal: StateFlow<Goal> = _selectedGoal

    private val _selectedActivityLevel = MutableStateFlow(ActivityLevel.Sedentary)
    val selectedActivityLevel: StateFlow<ActivityLevel> = _selectedActivityLevel

    private val _formError = MutableStateFlow<String?>(null)
    val formError: StateFlow<String?> = _formError

    private val _showFormErrorDialog = MutableStateFlow(false)
    val showFormErrorDialog: StateFlow<Boolean> = _showFormErrorDialog


    fun onUnitChanged(unit: Units) {
        _selectedUnit.value = unit
    }

    fun onFirstNameChanged(name: String) {
        _userFirstName.value = name
    }

    fun onLastNameChanged(name: String) {
        _userLastName.value = name
    }

    fun onBirthdayChanged(birthday: String) {
        _birthday.value = birthday
    }

    fun onHeightFeetChanged(heightFeet: String) {
        _heightInFeet.value = heightFeet
    }

    fun onHeightInchesChanged(heightInches: String) {
        _heightInInches.value = heightInches
    }

    fun onHeightCmChanged(heightCm: String) {
        _heightInCm.value = heightCm
    }

    fun onWeightInPoundsChanged(pounds: String) {
        _weightInPounds.value = pounds
    }

    fun onWeightInKgChanged(kilograms: String) {
        _weightInKg.value = kilograms
    }

    fun onGenderChanged(gender: Gender) {
        _selectedGender.value = gender
    }

    fun onGoalChanged(goal: Goal) {
        _selectedGoal.value = goal
    }

    fun onActivityLevelChanged(activityLevel: ActivityLevel) {
        _selectedActivityLevel.value = activityLevel
    }

    fun setFormError(message: String) {
        _formError.value = message
        _showFormErrorDialog.value = true
    }

    fun dismissFormErrorDialog() {
        _showFormErrorDialog.value = false
    }

    fun validateAndSubmit(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val isImperial = selectedUnit.value == Units.Imperial

        val error = validateFirstName(userFirstName.value)
            ?: validateLastName(userLastName.value)
            ?: validateBirthday(birthday.value)
            ?: validateHeight(isImperial, heightInFeet.value, heightInInches.value, heightInCm.value)
            ?: validateWeight(isImperial, weightInPounds.value, weightInKg.value)

        if (error != null) {
            setFormError(error)
            onFailure()
        } else {
            submit(onSuccess, onFailure)
        }
    }

    fun submit(
        onSuccessfulSubmit: () -> Unit,
        onFailedSubmit: () -> Unit
    ) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
            isLenient = false
        }

        val birthDate = try {
            formatter.parse(birthday.value)
        } catch (e: Exception) {
            setFormError("Invalid date format. Please use yyyy-MM-dd.")
            onFailedSubmit()
            return
        }

        val heightCm: Double
        val weightKg: Double

        try {
            if (selectedUnit.value == Units.Imperial) {
                val feet = heightInFeet.value.toIntOrNull()
                    ?: throw IllegalArgumentException("Height (feet) is invalid.")
                val inches = heightInInches.value.toIntOrNull() ?: 0
                val pounds = weightInPounds.value.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Weight (lbs) is invalid.")

                heightCm = feetAndInchesToCm(feet, inches)
                weightKg = lbsToKg(pounds)
            } else {
                heightCm = heightInCm.value.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Height (cm) is invalid.")
                weightKg = weightInKg.value.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Weight (kg) is invalid.")
            }
        } catch (e: IllegalArgumentException) {
            setFormError(e.message ?: "Invalid input.")
            onFailedSubmit()
            return
        }

        val user = User(
            firstName = userFirstName.value,
            lastName = userLastName.value,
            birthDay = birthDate,
            heightCm = heightCm,
            weightKg = weightKg,
            gender = selectedGender.value,
            activityLevel = selectedActivityLevel.value,
            goal = selectedGoal.value,
            units = selectedUnit.value,
        )

        prefs.saveUser(user)
        onSuccessfulSubmit()
    }
}