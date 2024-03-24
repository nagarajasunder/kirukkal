package com.geekydroid.kirukkal.ui.room.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geekydroid.kirukkal.ui.room.model.RoomScreenState
import com.geekydroid.kirukkal.ui.room.screenevents.RoomScreenEvents
import com.geekydroid.kirukkal.ui.room.viewmodels.RoomViewModel

const val ROOM_BASE_ROUTE = "room"

@Composable
fun Room(
    viewModel:RoomViewModel = hiltViewModel(),
    navigateToGameScreen: (String,String,Boolean) -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle(initialValue = RoomScreenState.initialState)
    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when(event) {
                is RoomScreenEvents.NavigateToGameScreen -> navigateToGameScreen(event.roomId,event.userName,event.isAdmin)
            }
        }
    }
    RoomScreenContent(state = screenState, actions = viewModel)
}