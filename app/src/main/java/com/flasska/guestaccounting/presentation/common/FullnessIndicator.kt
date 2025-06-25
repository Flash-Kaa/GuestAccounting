package com.flasska.guestaccounting.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.flasska.guestaccounting.R

@Composable
fun FullnessIndicator(
    fraction: Float,
    modifier: Modifier = Modifier,
) {
    var stateFraction by remember { mutableStateOf(0f) }
    val animatedFraction = animateFloatAsState(
        targetValue = stateFraction,
        animationSpec = tween(500, easing = EaseInOut)
    )
    var stateColor by remember { mutableStateOf(Color.Green) }
    val animatedColor = animateColorAsState(
        targetValue = stateColor,
        animationSpec = tween(750, easing = EaseInOut)
    )

    LaunchedEffect(stateFraction) {
        stateColor = when {
            stateFraction > 0.8f -> Color.Red
            stateFraction > 0.4f -> Color.Yellow
            else -> Color.Green
        }
    }

    LaunchedEffect(fraction) {
        stateFraction = fraction
    }

    Box(
        modifier = modifier
            .height(dimensionResource(R.dimen.indicator_height))
            .fillMaxWidth(animatedFraction.value)
            .clip(CircleShape)
            .background(animatedColor.value)
    )
}
