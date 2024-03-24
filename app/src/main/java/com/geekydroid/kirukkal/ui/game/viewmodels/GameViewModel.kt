package com.geekydroid.kirukkal.ui.game.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.kirukkal.GAME_STATUS_IDLE
import com.geekydroid.kirukkal.GAME_STATUS_STARTED
import com.geekydroid.kirukkal.core.network.websockets.actions.GameWsActions
import com.geekydroid.kirukkal.core.network.websockets.listeners.GameWebSocketListener
import com.geekydroid.kirukkal.ui.game.composables.GameScreenState
import com.geekydroid.kirukkal.ui.game.model.Chat
import com.geekydroid.kirukkal.ui.game.model.GameWords
import com.geekydroid.kirukkal.ui.game.model.Line
import com.geekydroid.kirukkal.ui.game.model.PlayerCreated
import com.geekydroid.kirukkal.ui.game.screenactions.GameScreenActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameWebSocketListener: GameWebSocketListener
): ViewModel(), GameScreenActions, GameWsActions {

    private val _screenState:MutableStateFlow<GameScreenState> = MutableStateFlow(GameScreenState.initialState)
    val screenState:StateFlow<GameScreenState> = _screenState

    fun connectToGame(roomId:String,playerName:String,isAdmin: Boolean) {
        updateScreenState(_screenState.value.copy(userName = playerName, isAdmin = isAdmin))
        val url = "ws://192.168.1.9:3000/room/${roomId}/players/${playerName}/create?is_admin=${isAdmin}"
        gameWebSocketListener.initiateConnection(url,this)
    }

    override fun onChatMessageChange(newValue: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(chatMessage = newValue))
        }
    }

    override fun onChatSendClick() {
        viewModelScope.launch {
            val chat = Chat(sender = _screenState.value.userId, message = _screenState.value.chatMessage)
            if (_screenState.value.gameStatus == GAME_STATUS_STARTED) {
                gameWebSocketListener.sendGameChatMessage(chat)
            } else {
                gameWebSocketListener.sendChatMessage(chat)
            }
            updateScreenState(_screenState.value.copy(chatMessage = ""))
        }
    }

    override fun onStartGameClick() {
        gameWebSocketListener.startGame()
    }

    override fun onGameWordChoose(word: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(showChooseGameWordScreen = false))
            gameWebSocketListener.sendSelectedGameWord(word)
        }
    }

    override fun onDraw(line: Line) {
        viewModelScope.launch {
            gameWebSocketListener.sendDrawMessage(line)
        }
    }

    override fun onNewChatMessage(chat: Chat) {
        viewModelScope.launch {
            val chats = _screenState.value.chats
            chats.add(chat)
            updateScreenState(_screenState.value.copy(chats = chats))
        }
    }

    override fun onPlayerCreated(createdPlayer: PlayerCreated) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(userId = createdPlayer.id))
        }
    }

    override fun onGameStatusChange(gameStatus: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(gameStatus = gameStatus))
        }
    }

    override fun onChooseGameWord(gameWords: GameWords) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(showChooseGameWordScreen = true, gameWords = gameWords.words))
        }
    }

    override fun onDrawingPlayerUpdated(player: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(drawingUserId = player))
        }
    }

    override fun onGameMessage(gameMessage: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(gameMessage = gameMessage))
        }
    }

    override fun streamDrawMessage(line: Line) {
        viewModelScope.launch {
            val newLines = _screenState.value.lines
            newLines.add(line)
            updateScreenState(_screenState.value.copy(lines = newLines))
        }
    }

    private fun updateScreenState(newState:GameScreenState) : GameScreenState {
        return _screenState.updateAndGet {
            newState
        }
    }
}