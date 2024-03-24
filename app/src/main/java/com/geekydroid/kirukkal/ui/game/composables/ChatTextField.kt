package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatTextField(
    modifier:Modifier = Modifier,
    message:String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(4.dp),
        value = message,
        onValueChange = onValueChange,
        maxLines = 2,
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            IconButton(
                onClick = onSend,
                enabled = message.trim().isNotEmpty()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "")
            }
        }
    )
}