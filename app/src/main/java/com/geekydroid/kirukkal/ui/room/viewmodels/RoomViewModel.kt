package com.geekydroid.kirukkal.ui.room.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekydroid.kirukkal.core.network.Endpoints
import com.geekydroid.kirukkal.core.network.NetworkResult
import com.geekydroid.kirukkal.core.network.websockets.actions.RoomWsActions
import com.geekydroid.kirukkal.core.network.websockets.listeners.RoomWebSocketListener
import com.geekydroid.kirukkal.core.network.websockets.listeners.model.RoomCreationMessage
import com.geekydroid.kirukkal.ui.room.model.Room
import com.geekydroid.kirukkal.ui.room.model.RoomScreenState
import com.geekydroid.kirukkal.ui.room.repository.RoomRepository
import com.geekydroid.kirukkal.ui.room.screenactions.RoomScreenActions
import com.geekydroid.kirukkal.ui.room.screenevents.RoomScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomWebSocketListener: RoomWebSocketListener,
    private val repository: RoomRepository
) : ViewModel(), RoomScreenActions, RoomWsActions {

    private val _screenState: MutableStateFlow<RoomScreenState> =
        MutableStateFlow(RoomScreenState.initialState)
    val screenState: StateFlow<RoomScreenState> = _screenState

    private val eventsChannel: Channel<RoomScreenEvents> = Channel()
    val events: Flow<RoomScreenEvents> = eventsChannel.receiveAsFlow()

    override fun onUserNameChange(newValue: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(userName = newValue))
        }
    }


    override fun onRoomSelected(room: Room) {
        viewModelScope.launch {
            eventsChannel.send(
                RoomScreenEvents.NavigateToGameScreen(
                    roomId = room.id,
                    userName = _screenState.value.userName,
                    isAdmin = false
                )
            )
        }
    }

    override fun onNewRoomNameChange(newValue: String) {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(newRoomName = newValue))
        }
    }

    override fun onNewRoomCreated() {
        viewModelScope.launch {
            when(val result = repository.CreateRoom(_screenState.value.newRoomName)) {
                is NetworkResult.Error -> Unit
                is NetworkResult.Exception -> Unit
                is NetworkResult.Success -> {
                    roomWebSocketListener.closeConnection()
                    eventsChannel.send(
                        RoomScreenEvents.NavigateToGameScreen(
                            roomId = result.data.roomId,
                            userName = _screenState.value.userName,
                            isAdmin = true
                        )
                    )
                }
            }
        }
    }

    override fun onUserCreate() {
        viewModelScope.launch {
            updateScreenState(_screenState.value.copy(showUserNameCard = false))
            roomWebSocketListener.initiateConnection(Endpoints.SUBSCRIBE_FOR_ROOM_UPDATES,this@RoomViewModel)
        }
    }

    private fun updateScreenState(newState: RoomScreenState): RoomScreenState {
        return _screenState.updateAndGet {
            newState
        }
    }

    override fun onNewRoomCreated(roomCreationMessage: RoomCreationMessage) {
        viewModelScope.launch {
            val availableRooms = _screenState.value.availableRooms
            availableRooms.add(
                Room(
                    id = roomCreationMessage.roomId,
                    name = roomCreationMessage.roomName
                )
            )
            updateScreenState(_screenState.value.copy(availableRooms = availableRooms))
        }
    }

}