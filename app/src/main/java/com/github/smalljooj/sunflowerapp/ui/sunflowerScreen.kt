package com.github.smalljooj.sunflowerapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.github.smalljooj.sunflowerapp.ui.home.HomeScreen
import com.github.smalljooj.sunflowerapp.ui.loading.LoadingScreen
import com.github.smalljooj.sunflowerapp.ui.profile.ProfileScreen
import com.github.smalljooj.sunflowerapp.ui.welcome.WelcomeScreen

enum class SunflowerScreen {
    START,
    WELCOME,
    PROFILE,
    HOME
}

@Composable
fun SunflowerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SunflowerScreen.START.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = SunflowerScreen.START.name) {
                LoadingScreen(
                    goToWelcomeScreen = {
                        navController.popBackStack(SunflowerScreen.START.name, inclusive = true)
                        navController.navigate(route = SunflowerScreen.WELCOME.name)
                    },
                    goToHomeScreen = {
                        navController.popBackStack(SunflowerScreen.START.name, inclusive = true)
                        navController.navigate(route = SunflowerScreen.HOME.name)
                    }
                )
            }
            composable(route = SunflowerScreen.WELCOME.name) {
                WelcomeScreen(
                    goToProfileScreen = {
                        navController.popBackStack(SunflowerScreen.WELCOME.name, inclusive = true)
                        navController.navigate(route = SunflowerScreen.PROFILE.name)
                    }
                )
            }
            composable(route = SunflowerScreen.PROFILE.name) {
                ProfileScreen(
                    goToHomeScreen = {
                        navController.popBackStack(SunflowerScreen.PROFILE.name, inclusive = true)
                        navController.navigate(route = SunflowerScreen.HOME.name)
                    }
                )
            }
            composable(route = SunflowerScreen.HOME.name) {
                HomeScreen()
            }
        }
    }
}