package com.geekydroid.kirukkal.core.network

import com.geekydroid.kirukkal.ui.room.model.CreateRoomResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApiService {

    @GET(Endpoints.CREATE_ROOM)
    suspend fun createRoom(@Path("name") roomName:String):Response<CreateRoomResponse>
}