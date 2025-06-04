package com.dante.macrovia.viewmodel

import androidx.lifecycle.ViewModel
import com.dante.macrovia.repository.SharedPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: SharedPrefsRepository
) : ViewModel() {

    fun userExists(): Boolean {
        return prefs.userExists()
    }
}