package com.geekydroid.kirukkal.core.network.websockets.listeners

import android.util.Log
import com.geekydroid.kirukkal.core.network.websockets.actions.RoomWsActions
import com.geekydroid.kirukkal.core.network.websockets.listeners.model.RoomCreationMessage
import com.geekydroid.kirukkal.core.network.websockets.listeners.model.WebSocketMessage
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RoomWebSocketListener"

@Singleton
class RoomWebSocketListener @Inject constructor(private val gson: Gson) : WebSocketListener() {

    private lateinit var websocketConn:WebSocket
    private lateinit var roomSocketActions:RoomWsActions


    fun initiateConnection(url:String,actions: RoomWsActions) {
        roomSocketActions = actions
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url(url).build()
        client.newWebSocket(request,this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        websocketConn = webSocket
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(TAG, "onFailure: ${t.message}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "onMessage: $text")
        try {
            val websocketMessage = gson.fromJson(text, WebSocketMessage::class.java)
            val roomCreationMessage = gson.fromJson(websocketMessage.message, RoomCreationMessage::class.java)
            roomSocketActions.onNewRoomCreated(roomCreationMessage)
        } catch (e:Exception){
            Log.d(TAG, "onMessage: Error onMessage ${e.message}")
        }
    }

    fun closeConnection() {
        websocketConn.close(1000,reason = null)
    }
}