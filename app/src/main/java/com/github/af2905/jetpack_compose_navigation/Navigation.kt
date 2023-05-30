package com.github.af2905.jetpack_compose_navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.af2905.jetpack_compose_navigation.Routes.DETAIL_SCREEN
import com.github.af2905.jetpack_compose_navigation.Routes.FAVORITE_SCREEN
import com.github.af2905.jetpack_compose_navigation.Routes.HOME_SCREEN
import com.github.af2905.jetpack_compose_navigation.screen.DetailScreen
import com.github.af2905.jetpack_compose_navigation.screen.FavoriteScreen
import com.github.af2905.jetpack_compose_navigation.screen.HomeScreen
import com.github.af2905.jetpack_compose_navigation.ui.theme.ColorAccent

data class Item(val route: String, val icon: ImageVector)

@Composable
fun BottomNavigationItems(navController: NavController) {
    val itemList = listOf(
        Item(route = HOME_SCREEN, icon = Icons.Filled.Home),
        Item(route = FAVORITE_SCREEN, icon = Icons.Filled.Favorite)
    )

    BottomNavigation {
        val destinationChanged: MutableState<String?> = remember { mutableStateOf(null) }
        val isInParentRoute = itemList.firstOrNull { it.route == destinationChanged.value } != null
        val parentRoute: MutableState<String?> =
            remember(destinationChanged.value) { mutableStateOf(HOME_SCREEN) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        fun selectedBottomNavigationItem() = if (isInParentRoute) {
            parentRoute.value = currentRoute
            currentRoute
        } else {
            parentRoute.value
        }

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            destinationChanged.value = navDestination.route
        }

        itemList.forEach { item ->
            val isSelected = selectedBottomNavigationItem() == item.route
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                selectedContentColor = ColorAccent,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun SetupNavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        composable(route = HOME_SCREEN) {
            HomeScreen(onItemClick = { navController.navigate(DETAIL_SCREEN) })
        }
        composable(route = DETAIL_SCREEN) {
            DetailScreen()
        }
        composable(FAVORITE_SCREEN) {
            FavoriteScreen()
        }
    }
}

@Composable
fun TopBarNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavigationItemRouteList = listOf(HOME_SCREEN, FAVORITE_SCREEN)

    val title = when (currentRoute) {
        HOME_SCREEN -> stringResource(id = R.string.screen_home_title)
        FAVORITE_SCREEN -> stringResource(id = R.string.screen_favorite_title)
        DETAIL_SCREEN -> stringResource(id = R.string.screen_detail_title)
        else -> ""
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        backgroundColor = colorResource(id = R.color.colorPrimaryDark),
        navigationIcon =
        if (navController.previousBackStackEntry != null &&
            !bottomNavigationItemRouteList.contains(currentRoute)
        ) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        } else {
            null
        }
    )
}

object Routes {
    const val HOME_SCREEN: String = "home_screen"
    const val FAVORITE_SCREEN: String = "favorite_screen"
    const val DETAIL_SCREEN: String = "detail_screen"
}