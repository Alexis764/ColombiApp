package com.project.colombiapp.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.colombiapp.core.preferences.FirstTimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val firstTimeManager: FirstTimeManager
) : ViewModel() {

    fun setFirstTime() {
        viewModelScope.launch {
            firstTimeManager.saveFirstTime(false)
        }
    }

}