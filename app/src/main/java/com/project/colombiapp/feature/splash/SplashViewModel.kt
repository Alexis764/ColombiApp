package com.project.colombiapp.feature.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.colombiapp.core.preferences.FirstTimeManager
import com.project.colombiapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firstTimeManager: FirstTimeManager
) : ViewModel() {

    private val _startNextScreen = MutableLiveData<Boolean>()
    val startNextScreen: LiveData<Boolean> = _startNextScreen

    private val _nextScreenRoute = MutableLiveData<String>()
    val nextScreenRoute: LiveData<String> = _nextScreenRoute

    private val splashTimer: CountDownTimer = object : CountDownTimer(2000, 1000) {
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            viewModelScope.launch {
                firstTimeManager.getFirstTime().collect { firstTime ->
                    _nextScreenRoute.value =
                        if (firstTime) Routes.WelcomeScreen.route
                        else Routes.HomeScreen.route
                    _startNextScreen.value = true
                }
            }
        }
    }.start()

}