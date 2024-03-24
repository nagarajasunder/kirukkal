package com.geekydroid.kirukkal.ui.game.model

import com.google.gson.annotations.SerializedName

data class PlayerCreated(
    @SerializedName("player_id")
    val id:String,
    @SerializedName("player_name")
    val name:String
)