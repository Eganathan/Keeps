package online.onenut.keeps.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun EditComponent(
    isEditClicked: MutableState<Boolean>,
    titleTSF: MutableState<TextFieldValue>,
    descriptionTSF: MutableState<TextFieldValue>,
    isSavedKeep: Boolean,
    onTitleClick: () -> Unit,
    onClose: () -> Unit,
    onDeleteClicked: () -> Unit = {}
) {
    val fr = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = isEditClicked.value)
    {
        if (isEditClicked.value) {
            delay(250)
            fr.requestFocus()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.onPrimary,
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(min = 25.dp)
        )
        {
            Column(verticalArrangement = Arrangement.Top) {
                if (isEditClicked.value) {
                    TextField(
                        value = titleTSF.value,
                        onValueChange = { titleTSF.value = it },
                        placeholder = {
                            BasicText(
                                text = "Title",
                                style = TextStyle(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(0.dp)
                            )
                        },
                        textStyle = TextStyle(fontWeight = FontWeight.Bold),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White
                        ),
                        maxLines = 1,
                        shape = RectangleShape,
                        modifier = Modifier
                            .heightIn(min = 15.dp)
                            .offset(y = -(5).dp)
                            .clip(RoundedCornerShape(5.dp)),
                    )

                    TextField(
                        value = descriptionTSF.value,
                        onValueChange = { descriptionTSF.value = it },
                        placeholder = {
                            BasicText(
                                text = "Take a note...",
                                modifier = Modifier.padding(0.dp)
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White
                        ),
                        maxLines = 15,
                        shape = RectangleShape,
                        modifier = Modifier
                            .offset(y = -(20).dp)
                            .clip(RoundedCornerShape(5.dp))
                            .focusRequester(fr)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = if (isSavedKeep) Arrangement.SpaceBetween else Arrangement.End
                    ) {

                        if (isSavedKeep)
                            IconButton(
                                onClick = { onDeleteClicked.invoke() },
                                modifier = Modifier
                                    .clip(CircleShape),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.error.copy(alpha = 0.6f)
                                )
                            }

                        Text(text = "close",
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .clickable {
                                    onClose.invoke()
                                    isEditClicked.value = !isEditClicked.value
                                })
                    }
                } else {
                    Text(text = "Take a note...",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .padding(vertical = 5.dp)
                            .fillMaxWidth()
                            .heightIn(min = 25.dp)
                            .clickable {
                                onTitleClick.invoke()
                                isEditClicked.value = !isEditClicked.value
                            })
                }
            }
        }
    }
}