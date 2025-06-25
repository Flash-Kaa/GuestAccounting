package com.flasska.guestaccounting.presentation.common.guest

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.flasska.guestaccounting.domain.model.Guest

@Composable
fun AnimatedGuestColorBackground(sideOfWedding: Guest.SideOfWedding): Color {
    val baseColor = when (sideOfWedding) {
        Guest.SideOfWedding.GROOM -> Color(0xFFE4F7E9)
        Guest.SideOfWedding.BRIDE -> Color(0xFFFFEBEF)
        Guest.SideOfWedding.NEUTRAL -> Color(0xFFF2F2F2)
    }

    val aroundWhiteColor = when (sideOfWedding) {
        Guest.SideOfWedding.GROOM -> Color(0xFFF3FBF4)
        Guest.SideOfWedding.BRIDE -> Color(0xFFFFF5F7)
        Guest.SideOfWedding.NEUTRAL -> Color.White
    }

    val transition = rememberInfiniteTransition(label = "BackgroundPulse")

    val pulseProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseProgress"
    )

    return interpolateColor(baseColor, aroundWhiteColor, pulseProgress)
}

fun interpolateColor(from: Color, to: Color, fraction: Float): Color {
    return Color(
        red = from.red + (to.red - from.red) * fraction,
        green = from.green + (to.green - from.green) * fraction,
        blue = from.blue + (to.blue - from.blue) * fraction,
        alpha = from.alpha + (to.alpha - from.alpha) * fraction
    )
}
