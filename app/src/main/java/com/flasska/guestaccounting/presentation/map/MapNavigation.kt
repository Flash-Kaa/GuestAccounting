package com.flasska.guestaccounting.presentation.map

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flasska.guestaccounting.utils.LocalAppComponent
import kotlinx.serialization.Serializable

@Serializable
internal data object MapRoute

fun NavController.navigateToMap() {
    navigate(MapRoute)
}

internal fun NavGraphBuilder.mapScreen() {
    composable<MapRoute> {
        MapScreen(
            viewModel = viewModel(
                factory = LocalAppComponent.provideMapViewModelFactory()
            )
        )
    }
}
