package com.github.smalljooj.sunflowerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.smalljooj.sunflowerapp.ui.SunflowerApp
import com.github.smalljooj.sunflowerapp.ui.theme.SunflowerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SunflowerAppTheme {
                SunflowerApp()
            }
        }
    }
}