package de.lijucay.rounded_screen_container

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ScreenContainer(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentBackground: Color = MaterialTheme.colorScheme.surface,
    defaultCornerRadius: Dp = 40.dp,
    noBottomBarCornerRadius: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        // Exclude navigation bars from safe drawing to achieve full edge to edge. This prevents the navigation bar to use a white or grey color if no bottom bar is used
        contentWindowInsets = WindowInsets.safeDrawing.exclude(WindowInsets.navigationBars),
        containerColor = containerColor,
        topBar = topBar,
        bottomBar = { bottomBar?.invoke() }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = defaultCornerRadius,
                        topEnd = defaultCornerRadius,
                        bottomEnd = bottomBar?.let { defaultCornerRadius } ?: noBottomBarCornerRadius,
                        bottomStart = bottomBar?.let { defaultCornerRadius } ?: noBottomBarCornerRadius
                    )
                )
                .background(contentBackground)
                .animateContentSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = defaultCornerRadius - 20.dp,
                            topEnd = defaultCornerRadius - 20.dp,
                            bottomStart = defaultCornerRadius - 20.dp,
                            bottomEnd = defaultCornerRadius - 20.dp
                        )
                    )
                    .animateContentSize(),
                content = content
            )
        }
    }
}