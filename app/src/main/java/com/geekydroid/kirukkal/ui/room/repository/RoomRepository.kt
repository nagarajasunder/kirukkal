package com.geekydroid.kirukkal.ui.room.repository

import com.geekydroid.kirukkal.core.network.NetworkResult
import com.geekydroid.kirukkal.ui.room.model.CreateRoomResponse

interface RoomRepository {
    suspend fun CreateRoom(roomName:String) : NetworkResult<CreateRoomResponse>
}