package br.edu.ifpb.sunflower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.edu.ifpb.sunflower.ui.theme.SunFlowerTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SunFlowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun App() {
    var page = remember{ mutableStateOf(1) }
    Box(modifier = Modifier) {
        when(page.value) {
            1 -> {
                OnBoarding(page)
            }
            2 -> {
                Profile(page)
            }
            3 -> {
                Home()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SunFlowerTheme {
        App()
    }
}
