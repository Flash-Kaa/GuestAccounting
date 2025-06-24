package com.flasska.guestaccounting.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.flasska.guestaccounting.presentation.map.MapRoute
import com.flasska.guestaccounting.presentation.map.mapScreen

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        startDestination = MapRoute,
        navController = navController,
        modifier = modifier
    ) {
        mapScreen()
    }
}
