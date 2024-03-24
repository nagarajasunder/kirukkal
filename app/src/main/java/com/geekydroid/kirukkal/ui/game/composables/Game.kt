package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.geekydroid.kirukkal.ui.game.viewmodels.GameViewModel

const val GAME_SCREEN_BASE_ROUTE = "game"
const val GAME_SCREEN_ARG_USER_ID = "USER_ID"
const val GAME_SCREEN_ARG_ROOM_ID = "ROOM_ID"
const val GAME_SCREEN_ARG_IS_ADMIN = "IS_ADMIN"
const val GAME_SCREEN_ROUTE = "${GAME_SCREEN_BASE_ROUTE}/{$GAME_SCREEN_ARG_ROOM_ID}/{$GAME_SCREEN_ARG_USER_ID}/{$GAME_SCREEN_ARG_IS_ADMIN}"

fun buildGameScreenRoute(roomId:String,userId:String,isAdmin:Boolean) : String = "${GAME_SCREEN_BASE_ROUTE}/${roomId}/${userId}/${isAdmin}"

@Composable
fun Game(
    viewModel:GameViewModel = hiltViewModel(),
    navBackStackEntry: NavBackStackEntry
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val roomId = navBackStackEntry.arguments?.getString(GAME_SCREEN_ARG_ROOM_ID)?:""
    val userId = navBackStackEntry.arguments?.getString(GAME_SCREEN_ARG_USER_ID)?:""
    val isAdmin = navBackStackEntry.arguments?.getBoolean(GAME_SCREEN_ARG_IS_ADMIN)?:false

    LaunchedEffect(key1 = Unit) {
        viewModel.connectToGame(roomId,userId,isAdmin)
    }

    GameScreenContent(
        state = state,
        action = viewModel
    )

}
