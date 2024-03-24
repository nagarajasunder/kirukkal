package com.geekydroid.kirukkal.core.network.websockets.actions

import com.geekydroid.kirukkal.core.network.websockets.listeners.model.RoomCreationMessage

interface RoomWsActions {

    fun onNewRoomCreated(roomCreationMessage: RoomCreationMessage)
}