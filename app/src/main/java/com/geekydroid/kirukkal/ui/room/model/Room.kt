package com.geekydroid.kirukkal.ui.room.model

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("room_id")
    val id:String,
    @SerializedName("room_name")
    val name:String
)
