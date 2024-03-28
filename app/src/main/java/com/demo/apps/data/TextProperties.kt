package com.demo.apps.data

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.apps.viewModel.TextListViewModel
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
data class TextProperties(
    var text: String,
    var fontSize: TextUnit = 16.sp,
    var color: Color = Color.Black,
    var fontWeight: FontWeight = FontWeight.Normal,
    var fontFamily: FontFamily? = null,
    var letterSpacing: TextUnit = TextUnit.Unspecified,
    var lineHeight: TextUnit = TextUnit.Unspecified,
    var textDecoration: androidx.compose.ui.text.style.TextDecoration? = null,
    var textAlign: androidx.compose.ui.text.style.TextAlign? = null,
    var overflow: androidx.compose.ui.text.style.TextOverflow = androidx.compose.ui.text.style.TextOverflow.Clip,
    var maxLines: Int = Int.MAX_VALUE,
    var softWrap: Boolean = true,
    // Add other properties as needed
) {

    fun toTextStyle(): TextStyle {
        return TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            color = color,
            lineHeight = lineHeight,
            textDecoration = textDecoration,
            textAlign = textAlign
        )
    }

    @Composable
    fun loadCompose(modifier: Modifier = Modifier,navController: NavController,viewModel:TextListViewModel,position:Int) {
        var textOffset by remember { mutableStateOf(IntOffset.Zero) }
        //TODO We have to implement edit and delete button based on isShowEditDelete boolean
        var isShowEditDelete by remember { mutableStateOf(Boolean) }

        Text(
            text = text,
            style = toTextStyle(),
            maxLines = maxLines,
            overflow = overflow,
            softWrap = softWrap,
            modifier = modifier
                .offset { textOffset }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            viewModel.textPropertiesModel.value = Pair(position,this@TextProperties)
                            navController.navigate(NavigationPath.textEditingScreen)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        textOffset = IntOffset(
                            (textOffset.x + dragAmount.x).roundToInt(),
                            (textOffset.y + dragAmount.y).roundToInt()
                        )
                    }
                }
            )
    }
}
