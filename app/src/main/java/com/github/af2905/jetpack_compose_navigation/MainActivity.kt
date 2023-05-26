package com.github.af2905.jetpack_compose_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.af2905.jetpack_compose_navigation.screen.MainScreen
import com.github.af2905.jetpack_compose_navigation.ui.theme.JetpackComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val navController: NavHostController = rememberNavController()
            JetpackComposeNavigationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                }
            }
        }
    }
}