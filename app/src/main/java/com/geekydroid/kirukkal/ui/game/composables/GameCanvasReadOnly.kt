package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.geekydroid.kirukkal.ui.game.model.Line

@Composable
fun GameCanvasReadOnly(
    modifier: Modifier = Modifier,
    lines:List<Line>
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        lines.forEach { line ->
            drawLine(
                color = Color.Black,
                start = Offset(line.startX, line.startY),
                end = Offset(line.endX, line.endY)
            )
        }
    }
}