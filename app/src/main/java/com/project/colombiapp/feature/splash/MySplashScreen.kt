package com.project.colombiapp.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.colombiapp.R

@Composable
fun MySplashScreen(
    navController: NavHostController = rememberNavController(),
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val startNextScreen: Boolean by splashViewModel.startNextScreen.observeAsState(false)
    val nextScreenRoute: String by splashViewModel.nextScreenRoute.observeAsState("")

    if (startNextScreen) {
        navController.popBackStack()
        navController.navigate(nextScreenRoute)
    }

    Image(
        painter = painterResource(id = R.drawable.splash_screen),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}


@Preview(showSystemUi = true)
@Composable
private fun MyPreview() {
    MySplashScreen()
}