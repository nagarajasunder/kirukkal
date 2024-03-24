package com.geekydroid.kirukkal.ui.game.model

import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("start_x")
    val startX: Float,
    @SerializedName("start_y")
    val startY: Float,
    @SerializedName("end_x")
    val endX:Float,
    @SerializedName("end_y")
    val endY:Float
)
