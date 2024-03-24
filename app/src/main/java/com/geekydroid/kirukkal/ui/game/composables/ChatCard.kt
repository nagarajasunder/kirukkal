package com.geekydroid.kirukkal.ui.game.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekydroid.kirukkal.R
import com.geekydroid.kirukkal.core.utils.DATE_TIME_FORMATS
import com.geekydroid.kirukkal.core.utils.DateTimeUtils
import com.geekydroid.kirukkal.ui.game.model.Chat

@Composable
fun ChatCard(
    modifier:Modifier = Modifier,
    receiver:String,
    chat:Chat
) {
    val contentAlignment = if (chat.sender == receiver) Alignment.TopEnd else Alignment.TopStart
    val paddingModifier =
        if (chat.sender == receiver) modifier.padding(top = 4.dp, end = 6.dp) else modifier.padding(
            top = 4.dp,
            start = 6.dp
        )
    Surface {
        Box(
            modifier = paddingModifier
                .fillMaxWidth(),
            contentAlignment = contentAlignment
        ) {
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier
                    .padding(8.dp)
                    .width(IntrinsicSize.Max)
                ) {
                    val sender =
                        if (chat.sender == receiver) stringResource(id = R.string.you) else chat.sender
                    Text(
                        text = sender,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = chat.message)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = DateTimeUtils.convertMillisToDateTimeFormat(
                            chat.time,
                            DATE_TIME_FORMATS.HH_MM_AA
                        ),
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.End, fontStyle = FontStyle.Italic, fontSize = 14.sp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}