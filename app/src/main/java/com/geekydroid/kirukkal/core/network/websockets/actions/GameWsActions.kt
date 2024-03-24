package com.geekydroid.kirukkal.core.network.websockets.actions

import com.geekydroid.kirukkal.ui.game.model.Chat
import com.geekydroid.kirukkal.ui.game.model.GameWords
import com.geekydroid.kirukkal.ui.game.model.Line
import com.geekydroid.kirukkal.ui.game.model.PlayerCreated

interface GameWsActions {

    fun onNewChatMessage(chat: Chat)
    fun onPlayerCreated(createdPlayer: PlayerCreated)
    fun onGameStatusChange(gameStatus: String)
    fun onChooseGameWord(gameWords: GameWords)
    fun onDrawingPlayerUpdated(player: String)
    fun onGameMessage(gameMessage: String)
    fun streamDrawMessage(line: Line)
}