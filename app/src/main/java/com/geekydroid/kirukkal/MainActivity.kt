package com.geekydroid.kirukkal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        Room()
                    }
                }
            }
        }
    }
}
