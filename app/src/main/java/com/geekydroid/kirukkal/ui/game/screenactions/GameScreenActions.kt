package com.geekydroid.kirukkal.ui.game.screenactions

import com.geekydroid.kirukkal.ui.game.model.Line

interface GameScreenActions {

    fun onChatMessageChange(newValue:String)
    fun onChatSendClick()
    fun onStartGameClick()
    fun onGameWordChoose(word:String)
    fun onDraw(line: Line)

}