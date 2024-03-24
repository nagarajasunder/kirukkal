package com.geekydroid.kirukkal.ui.game.model

import com.google.gson.annotations.SerializedName

data class GameWords(
    @SerializedName("words")
    val words:List<String>
)
