package com.geekydroid.kirukkal.ui.room.model

import com.google.gson.annotations.SerializedName

data class CreateRoomResponse(
    @SerializedName("room_id")
    val roomId:String
)