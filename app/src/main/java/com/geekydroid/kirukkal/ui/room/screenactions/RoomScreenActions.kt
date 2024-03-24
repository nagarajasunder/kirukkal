package com.geekydroid.kirukkal.ui.room.screenactions

import com.geekydroid.kirukkal.ui.room.model.Room

interface RoomScreenActions {

    fun onUserNameChange(newValue:String)
    fun onRoomSelected(room:Room)
    fun onNewRoomNameChange(newValue:String)
    fun onNewRoomCreated()
    fun onUserCreate()
}