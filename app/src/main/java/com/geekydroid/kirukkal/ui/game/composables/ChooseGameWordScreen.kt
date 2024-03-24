package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekydroid.kirukkal.ui.theme.KirukkalAppTheme

@Composable
fun ChooseGameWordScreen(
    modifier: Modifier = Modifier,
    gameWords:List<String>,
    onWordChoose: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Choose a word to draw", style = MaterialTheme.typography.titleMedium)
        gameWords.forEach { word ->
            Card(
                modifier = Modifier.clickable { onWordChoose(word) }
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = word,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseGameWordScreenPreview() {
    KirukkalAppTheme {
        ChooseGameWordScreen(gameWords = listOf("Television","Refrigerator","Stack overflow")) {
            
        }
    }
}