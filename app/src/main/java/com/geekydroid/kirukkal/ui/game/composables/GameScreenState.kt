package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.geekydroid.kirukkal.GAME_STATUS_IDLE
import com.geekydroid.kirukkal.ui.game.model.Chat
import com.geekydroid.kirukkal.ui.game.model.Line

data class GameScreenState(
    val gameStatus:String,
    val gameMessage:String,
    val chats:SnapshotStateList<Chat>,
    val lines:SnapshotStateList<Line>,
    val gameWords:List<String>,
    val showChooseGameWordScreen:Boolean,
    val userId:String,
    val userName:String,
    val isAdmin:Boolean,
    val drawingUserId:String,
    val chatMessage:String
) {
    companion object {
        val initialState = GameScreenState(
            gameStatus = GAME_STATUS_IDLE,
            gameMessage = "",
            chats = mutableStateListOf(),
            lines = mutableStateListOf(),
            gameWords = listOf(),
            showChooseGameWordScreen = false,
            userId = "",
            userName = "",
            isAdmin = false,
            drawingUserId = "",
            chatMessage = ""
        )
    }
}
