package com.geekydroid.kirukkal.core.network.websockets.listeners.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class WebSocketMessage(
    @SerializedName("message_type")
    val messageType:String,
    @SerializedName("message")
    val message:JsonElement
)
