package online.onenut.keeps.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class KeepNotes(
    val ID: Long,
    var title: MutableState<String> = mutableStateOf(String()),
    var description: MutableState<String>,
    val createdTime: String,
    var modifiedTime: String,
    var isFavourite: Boolean = false,
    var color: Color? = null
)
