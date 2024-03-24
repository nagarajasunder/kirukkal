package com.geekydroid.kirukkal.ui.room.screenevents

sealed class RoomScreenEvents {

    data class NavigateToGameScreen(val roomId:String, val userName:String, val isAdmin:Boolean) : RoomScreenEvents()
}