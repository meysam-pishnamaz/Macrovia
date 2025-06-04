package com.dante.macrovia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dante.macrovia.model.Units
import com.dante.macrovia.viewmodel.UserProfileViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.macrovia.model.ActivityLevel
import com.dante.macrovia.model.Gender
import com.dante.macrovia.model.Goal
import com.dante.macrovia.model.InputType
import com.dante.macrovia.ui.components.BirthdayPicker
import com.dante.macrovia.ui.components.CustomMaxWidthButton
import com.dante.macrovia.ui.components.CustomTextField
import com.dante.macrovia.ui.components.FormErrorDialogModal
import com.dante.macrovia.ui.components.HeightInputField
import com.dante.macrovia.ui.components.SegmentedOptionSelector
import com.dante.macrovia.ui.components.WeightInputField

@Composable
fun UserProfileSetupScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    val viewModel: UserProfileViewModel = hiltViewModel()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val firstName by viewModel.userFirstName.collectAsState()
    val lastName by viewModel.userLastName.collectAsState()
    val birthday by viewModel.birthday.collectAsState()
    val heightFeet by viewModel.heightInFeet.collectAsState()
    val heightInches by viewModel.heightInInches.collectAsState()
    val heightCm by viewModel.heightInCm.collectAsState()
    val weightLbs by viewModel.weightInPounds.collectAsState()
    val weightKg by viewModel.weightInKg.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()
    val selectedGoal by viewModel.selectedGoal.collectAsState()
    val selectedActivityLevel by viewModel.selectedActivityLevel.collectAsState()
    val formError by viewModel.formError.collectAsState()
    val showDialog by viewModel.showFormErrorDialog.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Health Profile",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1.5f))
        }
        Column (
            Modifier
                .padding(20.dp)
                .padding(top = 10.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ){
            CustomTextField(
                titleText = "What’s your first name?",
                value = firstName,
                onValueChange = {
                    viewModel.onFirstNameChanged(it)
                },
                placeholderText = "First Name",
                inputType = InputType.NAME
            )
            CustomTextField(
                titleText = "What’s your last name?",
                value = lastName,
                onValueChange = {
                    viewModel.onLastNameChanged(it)
                },
                placeholderText = "Last Name",
                inputType = InputType.NAME
            )
            SegmentedOptionSelector(
                title = "Which measurement system do you prefer?",
                options = Units.entries,
                selectedOption = selectedUnit,
                onOptionSelected = { viewModel.onUnitChanged(it) },
                labelMapper = { unit ->
                    when (unit) {
                        Units.Imperial -> "US Customary"
                        Units.Metric -> "Metric"
                    }
                }
            )
            BirthdayPicker(
                titleText = "When is your birthday?",
                selectedDate = birthday,
                onDateSelected = { viewModel.onBirthdayChanged(it) }
            )
            HeightInputField(
                unit = selectedUnit,
                heightInFeet = heightFeet,
                heightInInches = heightInches,
                heightInCm = heightCm,
                onFeetChanged = { viewModel.onHeightFeetChanged(it)},
                onInchesChanged = { viewModel.onHeightInchesChanged(it) },
                onCmChanged = { viewModel.onHeightCmChanged(it) }
            )
            WeightInputField(
                unit = selectedUnit,
                weightInPound = weightLbs,
                weightInKg = weightKg,
                onPoundChanged = {viewModel.onWeightInPoundsChanged(it)},
                onKilogramChanged = {viewModel.onWeightInKgChanged(it)}
            )
            SegmentedOptionSelector(
                title = "How do you identify?",
                options = Gender.entries,
                selectedOption = selectedGender,
                onOptionSelected = { viewModel.onGenderChanged(it) },
                labelMapper = { gender ->
                    when (gender) {
                        Gender.Male -> "Male"
                        Gender.Female -> "Female"
                        Gender.PreferNotToSay -> "Prefer not to say"
                    }
                }
            )
            SegmentedOptionSelector(
                title = "What's your goal ?",
                options = Goal.entries,
                selectedOption = selectedGoal,
                onOptionSelected = {
                    viewModel.onGoalChanged(it) },
                labelMapper = { goal ->
                    when (goal) {
                        Goal.LoseWeight -> "Lose weight"
                        Goal.MaintainWeight -> "Maintain weight"
                        Goal.GainWeight -> "Gain weight"
                    }
                }
            )
            SegmentedOptionSelector(
                title = "What's your activity level ?",
                options = ActivityLevel.entries,
                selectedOption = selectedActivityLevel,
                onOptionSelected = {
                    viewModel.onActivityLevelChanged(it) },
                labelMapper = { activityLevel ->
                    when (activityLevel) {
                        ActivityLevel.Sedentary -> "Sedentary"
                        ActivityLevel.LightlyActive -> "Lightly active"
                        ActivityLevel.Active -> "Active"
                        ActivityLevel.VeryActive -> "Very active"
                    }
                }
            )
            CustomMaxWidthButton(label = "Submit") {
                viewModel.validateAndSubmit(
                    onSuccess = { /* navigate or show success */ },
                    onFailure = { /* optional logging */ }
                )
            }
        }
    }
    if (showDialog) {
        FormErrorDialogModal(
            message = formError!!,
            onDismiss = { viewModel.dismissFormErrorDialog() }
        )
    }
}