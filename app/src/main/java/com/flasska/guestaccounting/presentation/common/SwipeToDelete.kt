package com.flasska.guestaccounting.presentation.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

@Composable
infix fun Modifier.swipeWithAction(
    onEnd: () -> Unit,
): Modifier {

    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val swipeThreshold = 150f

    val dragState = rememberDraggableState { delta ->
        scope.launch {
            offsetX.snapTo((offsetX.value + delta).coerceAtMost(0f))
        }
    }

    return this
        .offset { IntOffset(offsetX.value.toInt(), 0) }
        .draggable(
            state = dragState,
            orientation = Orientation.Horizontal,
            onDragStopped = {
                if (offsetX.value <= -swipeThreshold) {
                    scope.launch {
                        offsetX.animateTo(-10000f, tween(500))
                        onEnd()
                    }
                } else {
                    scope.launch {
                        offsetX.animateTo(0f, tween(500))
                    }
                }
            }
        )
}
