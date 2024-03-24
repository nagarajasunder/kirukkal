package com.geekydroid.kirukkal.ui.game.model

import com.google.gson.annotations.SerializedName

data class Chat(
    @SerializedName("sender")
    val sender:String,
    @SerializedName("message")
    val message:String,
    @SerializedName("time")
    val time:Long = System.currentTimeMillis()
)
