package com.geekydroid.kirukkal.core.network

object Endpoints {
    const val CREATE_ROOM = "rooms/{name}/create"

    //Websocket Routes
    const val SUBSCRIBE_FOR_ROOM_UPDATES = "ws://192.168.1.9:3000/subscribe/room"
}