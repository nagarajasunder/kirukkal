package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekydroid.kirukkal.GAME_STATUS_IDLE
import com.geekydroid.kirukkal.R
import com.geekydroid.kirukkal.ui.game.screenactions.GameScreenActions

@Composable
fun GameScreenContent(
    modifier: Modifier = Modifier,
    state:GameScreenState,
    action:GameScreenActions
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {
        if (state.showChooseGameWordScreen) {
            ChooseGameWordScreen(gameWords = state.gameWords, onWordChoose = action::onGameWordChoose)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)

            ) {
                if (state.drawingUserId == state.userId) {
                    GameCanvas(onDraw = action::onDraw)
                } else {
                    GameCanvasReadOnly(lines = state.lines)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)) {
                    if (state.isAdmin && state.gameStatus == GAME_STATUS_IDLE) {
                        Button(onClick = action::onStartGameClick) {
                            Text(text = stringResource(id = R.string.start_game))
                        }
                    }
                    AnimatedVisibility(visible = state.gameMessage.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            text = state.gameMessage
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(state.chats.size) {
                            val chat = state.chats[it]
                            ChatCard(receiver = state.userId, chat = chat)
                        }
                    }
                }
                AnimatedVisibility(visible = state.drawingUserId != state.userId) {
                    Box {
                        ChatTextField(
                            message = state.chatMessage,
                            onValueChange = action::onChatMessageChange,
                            onSend = action::onChatSendClick
                        )
                    }
                }
            }
        }
    }
}

