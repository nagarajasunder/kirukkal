package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.geekydroid.kirukkal.ui.game.model.Line

@Composable
fun GameCanvas(
    modifier: Modifier = Modifier,
    onDraw: (Line) -> Unit
) {
    val lines = remember {
        mutableStateListOf<Line>()
    }
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val startPosition = change.position - dragAmount
                    val newLine = Line(
                        startX = startPosition.x,
                        startY = startPosition.y,
                        endX = change.position.x,
                        endY = change.position.y
                    )
                    onDraw(newLine)
                    lines.add(newLine)
                }
            }
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