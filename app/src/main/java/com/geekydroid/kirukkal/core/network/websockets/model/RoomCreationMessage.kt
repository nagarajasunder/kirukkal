package com.geekydroid.kirukkal.core.network.websockets.listeners.model

import com.google.gson.annotations.SerializedName

data class RoomCreationMessage(
    @SerializedName("room_id")
    val roomId:String,
    @SerializedName("room_name")
    val roomName:String
)
