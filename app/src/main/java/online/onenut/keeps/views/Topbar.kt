package online.onenut.keeps.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TopBar(
    searchTFS: MutableState<TextFieldValue>,
    searchState: MutableState<Boolean>,
    onSearchTyped: (String) -> Unit
) {
    val fr = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = searchState.value)
    {
        if (searchState.value) {
            delay(250)
            fr.requestFocus()
        }
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                }

                if (!searchState.value) {
                    Text(
                        text = "Keeps", style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        letterSpacing = 1.sp
                    )
                } else {
                    TextField(
                        value = searchTFS.value,
                        onValueChange = {
                            searchTFS.value = it;
                            onSearchTyped.invoke(it.text)
                        },
                        placeholder = {
                            BasicText(
                                text = "Search...",
                                modifier = Modifier.padding(0.dp)
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = MaterialTheme.colors.onPrimary
                        ),
                        maxLines = 1,
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .clip(RoundedCornerShape(5.dp))
                            .focusRequester(fr)

                    )
                }

                IconButton(
                    onClick = {
                        if (!searchState.value)
                            searchTFS.value = TextFieldValue()

                        searchState.value = !searchState.value
                    },
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                    )
                }
            }
        }
    )
}