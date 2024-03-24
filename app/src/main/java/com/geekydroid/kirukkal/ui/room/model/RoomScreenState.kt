package com.geekydroid.kirukkal.ui.room.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class RoomScreenState(
    val showUserNameCard:Boolean,
    val availableRooms:SnapshotStateList<Room>,
    val newRoomName:String,
    val userName:String,
) {
    companion object {
        val initialState =
            RoomScreenState(showUserNameCard = true, availableRooms = mutableStateListOf(), newRoomName = "", userName = "")
    }
}