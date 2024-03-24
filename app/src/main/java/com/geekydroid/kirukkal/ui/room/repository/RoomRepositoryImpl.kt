package com.geekydroid.kirukkal.ui.room.repository

import com.geekydroid.kirukkal.core.network.NetworkResult
import com.geekydroid.kirukkal.core.network.RoomApiService
import com.geekydroid.kirukkal.core.network.makeNetworkCall
import com.geekydroid.kirukkal.ui.room.model.CreateRoomResponse
import javax.inject.Inject


class RoomRepositoryImpl @Inject constructor(
    private val roomApiService: RoomApiService
) : RoomRepository {

    override suspend fun CreateRoom(roomName:String): NetworkResult<CreateRoomResponse> {
        return makeNetworkCall {
            roomApiService.createRoom(roomName)
        }

    }
}