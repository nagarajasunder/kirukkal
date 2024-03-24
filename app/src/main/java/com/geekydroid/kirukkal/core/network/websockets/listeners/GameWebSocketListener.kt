package com.geekydroid.kirukkal.core.network.websockets.listeners

import android.util.Log
import com.geekydroid.kirukkal.MESSAGE_TYPE_CHAT
import com.geekydroid.kirukkal.MESSAGE_TYPE_DRAW
import com.geekydroid.kirukkal.MESSAGE_TYPE_GAME_CHAT
import com.geekydroid.kirukkal.MESSAGE_TYPE_GAME_MESSAGE
import com.geekydroid.kirukkal.MESSAGE_TYPE_GAME_STATUS
import com.geekydroid.kirukkal.MESSAGE_TYPE_PLAYER_CHOOSED_WORD
import com.geekydroid.kirukkal.MESSAGE_TYPE_PLAYER_CREATED
import com.geekydroid.kirukkal.MESSAGE_TYPE_PLAYER_WORD_OPTIONS
import com.geekydroid.kirukkal.MESSAGE_TYPE_START_GAME
import com.geekydroid.kirukkal.MESSAGE_TYPE_STREAM_DRAW
import com.geekydroid.kirukkal.MESSAGE_TYPE_UPDATE_DRAWING_PLAYER
import com.geekydroid.kirukkal.core.di.ApplicationScope
import com.geekydroid.kirukkal.core.di.IoDispatcher
import com.geekydroid.kirukkal.core.network.websockets.actions.GameWsActions
import com.geekydroid.kirukkal.core.network.websockets.listeners.model.WebSocketMessage
import com.geekydroid.kirukkal.ui.game.model.Chat
import com.geekydroid.kirukkal.ui.game.model.GameWords
import com.geekydroid.kirukkal.ui.game.model.Line
import com.geekydroid.kirukkal.ui.game.model.PlayerCreated
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

;

private const val TAG = "GameWebSocketListener"

@Singleton
class GameWebSocketListener @Inject constructor(
    private val gson: Gson,
    @ApplicationScope private val externalScope:CoroutineScope,
    @IoDispatcher private val externalDispatcher: CoroutineDispatcher
) : WebSocketListener() {

    private lateinit var webSocket: WebSocket
    private lateinit var gameActions: GameWsActions

    fun initiateConnection(url: String, actions: GameWsActions) {
       externalScope.launch(externalDispatcher) {
           gameActions = actions
           val client = OkHttpClient.Builder().build()
           val request = Request.Builder().url(url).build()
           client.newWebSocket(request, this@GameWebSocketListener)
       }
    }

    fun sendGameChatMessage(chat: Chat) {
        externalScope.launch(externalDispatcher) {
            sendMessage(MESSAGE_TYPE_GAME_CHAT,chat)
        }
    }

    fun sendChatMessage(chat: Chat) {
        externalScope.launch(externalDispatcher) {
            sendMessage(MESSAGE_TYPE_CHAT,chat)
        }
    }

    private fun sendMessage(messageType:String,message:Any) {
        val messageJsonElement = gson.toJsonTree(message)
        val wsMessage = WebSocketMessage(messageType = messageType, message = messageJsonElement)
        val wsMessageStr = gson.toJson(wsMessage)
        webSocket.send(wsMessageStr)
    }


    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        this.webSocket = webSocket
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "onMessage: $text")
        val wsMessage: WebSocketMessage = gson.fromJson(text, WebSocketMessage::class.java)
        processSocketMessages(wsMessage)
    }

    private fun processSocketMessages(wsMessage: WebSocketMessage) {
        externalScope.launch(externalDispatcher) {
            when (wsMessage.messageType) {
                MESSAGE_TYPE_PLAYER_CREATED -> {
                    val message : PlayerCreated = gson.fromJson(wsMessage.message,PlayerCreated::class.java)
                    gameActions.onPlayerCreated(message)
                }
                MESSAGE_TYPE_PLAYER_WORD_OPTIONS -> {
                    val gameWords : GameWords = gson.fromJson(wsMessage.message,GameWords::class.java)
                    gameActions.onChooseGameWord(gameWords)
                }
                MESSAGE_TYPE_UPDATE_DRAWING_PLAYER -> {
                    val player:String = gson.fromJson(wsMessage.message,String::class.java)
                    gameActions.onDrawingPlayerUpdated(player)
                }
                MESSAGE_TYPE_GAME_MESSAGE -> {
                    val gameMessage:String = gson.fromJson(wsMessage.message,String::class.java)
                    gameActions.onGameMessage(gameMessage)
                }
                MESSAGE_TYPE_CHAT -> {
                    val chatMessage: Chat = gson.fromJson(wsMessage.message, Chat::class.java)
                    gameActions.onNewChatMessage(chatMessage)
                }
                MESSAGE_TYPE_GAME_STATUS -> {
                    val gameStatus = gson.fromJson(wsMessage.message,String::class.java)
                    gameActions.onGameStatusChange(gameStatus)
                }
                MESSAGE_TYPE_STREAM_DRAW -> {
                    val line : Line = gson.fromJson(wsMessage.message,Line::class.java)
                    gameActions.streamDrawMessage(line)
                }
            }
        }
    }

    fun startGame() {
        externalScope.launch {
            sendMessage(MESSAGE_TYPE_START_GAME,"")
        }
    }

    fun sendSelectedGameWord(word: String) {
        externalScope.launch(externalDispatcher) {
            sendMessage(MESSAGE_TYPE_PLAYER_CHOOSED_WORD,word)
        }
    }

    fun sendDrawMessage(line: Line) {
        externalScope.launch(externalDispatcher) {
            sendMessage(MESSAGE_TYPE_DRAW,line)
        }
    }

}