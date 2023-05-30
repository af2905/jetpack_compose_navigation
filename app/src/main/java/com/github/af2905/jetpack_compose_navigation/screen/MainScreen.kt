package com.github.af2905.jetpack_compose_navigation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.github.af2905.jetpack_compose_navigation.BottomNavigationItems
import com.github.af2905.jetpack_compose_navigation.SetupNavigationHost
import com.github.af2905.jetpack_compose_navigation.TopBarNavigation

@Composable
fun MainScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBarNavigation(navController = navController) },
        bottomBar = { BottomNavigationItems(navController = navController) }
    ) { padding ->
        SetupNavigationHost(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}