package com.geekydroid.kirukkal.ui.room.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geekydroid.kirukkal.R
import com.geekydroid.kirukkal.ui.room.model.Room
import com.geekydroid.kirukkal.ui.room.model.RoomScreenState
import com.geekydroid.kirukkal.ui.room.screenactions.RoomScreenActions

@Composable
fun RoomScreenContent(
    modifier: Modifier = Modifier,
    state:RoomScreenState,
    actions:RoomScreenActions
) {
    val verticalArrangement = if (state.showUserNameCard) Arrangement.Center else Arrangement.Top
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = verticalArrangement
    ) {
        if (state.showUserNameCard) {
            CreateUserCard(
                userName = state.userName,
                onUserNameChange = actions::onUserNameChange,
                onProceedClick = actions::onUserCreate
            )
        } else {
            CreateRoomCard(
                roomName = state.newRoomName,
                onRoomNameChange = actions::onNewRoomNameChange,
                onCreateRoomClick = actions::onNewRoomCreated
            )
            LazyColumn(
                modifier = Modifier.padding(top = 8.dp),
                ) {
                items(state.availableRooms.size) {
                    val room = state.availableRooms[it]
                    RoomCard(room = room, onRoomSelect = actions::onRoomSelected)
                }
            }
        }
    }
}

@Composable
fun RoomCard(
    modifier: Modifier = Modifier,
    room: Room,
    onRoomSelect: (Room) -> Unit
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .clickable { onRoomSelect(room) }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = room.name
        )
    }
}

@Composable
fun CreateRoomCard(
    modifier: Modifier = Modifier,
    roomName:String,
    onRoomNameChange: (String) -> Unit,
    onCreateRoomClick: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.create_new_room)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = roomName, onValueChange = onRoomNameChange
            )
            IconButton(
                onClick = onCreateRoomClick,
                enabled = roomName.isNotEmpty()
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(id = R.string.proceed)
                )
            }

        }
    }
}

@Composable
fun CreateUserCard(
    modifier: Modifier = Modifier,
    userName:String,
    onUserNameChange: (String) -> Unit,
    onProceedClick: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.welcome_to_kirukkals),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = userName,
                onValueChange = onUserNameChange,
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_a_name))
                }
            )
            SmallFloatingActionButton(onClick = onProceedClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = stringResource(id = R.string.proceed))
            }
        }
    }
}
