package com.geekydroid.kirukkal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.geekydroid.kirukkal.ui.game.composables.GAME_SCREEN_ARG_IS_ADMIN
import com.geekydroid.kirukkal.ui.game.composables.GAME_SCREEN_ARG_ROOM_ID
import com.geekydroid.kirukkal.ui.game.composables.GAME_SCREEN_ARG_USER_ID
import com.geekydroid.kirukkal.ui.game.composables.GAME_SCREEN_ROUTE
import com.geekydroid.kirukkal.ui.game.composables.Game
import com.geekydroid.kirukkal.ui.game.composables.buildGameScreenRoute
import com.geekydroid.kirukkal.ui.room.composables.ROOM_BASE_ROUTE
import com.geekydroid.kirukkal.ui.room.composables.Room
import com.geekydroid.kirukkal.ui.theme.KirukkalAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            KirukkalAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = ROOM_BASE_ROUTE
                ) {
                    composable(route = ROOM_BASE_ROUTE) {
                        Room { roomId, userName, isAdmin ->
                            navController.navigate(buildGameScreenRoute(roomId, userName,isAdmin))
                        }
                    }
                    composable(route = GAME_SCREEN_ROUTE, arguments = listOf(
                        navArgument(GAME_SCREEN_ARG_ROOM_ID) {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument(GAME_SCREEN_ARG_USER_ID) {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument(GAME_SCREEN_ARG_IS_ADMIN) {
                            type = NavType.BoolType
                            defaultValue = false
                        }
,                    )) {
                        Game(navBackStackEntry = it)
                    }
                }
            }
        }
    }
}
