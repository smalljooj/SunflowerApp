package com.github.smalljooj.sunflowerapp.ui.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.smalljooj.sunflowerapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    goToWelcomeScreen: () -> Unit,
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoadingViewModel = viewModel(factory = LoadingViewModel.Factory)
) {
    val scope = rememberCoroutineScope()
    if (viewModel.isLoading) {
        Column {
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LaunchedEffect(true) {
                    scope.launch {
                        delay(1000)
                        viewModel.verifySession(
                            goToWelcomeScreen= goToWelcomeScreen,
                            goToHomeScreen = goToHomeScreen
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.loading_icon),
                    tint = Color(0xFFa16b32),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier
                        .size(200.dp)
                )
            }
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "@SunflowerApp",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
