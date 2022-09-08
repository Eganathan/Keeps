package online.onenut.keeps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import online.onenut.keeps.entity.KeepNotes
import online.onenut.keeps.ui.theme.KeepsTheme
import online.onenut.keeps.views.KeepState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = remember {
                        KeepState()
                    }
                    App(state)
                }
            }
        }
    }
}

@Composable
fun App(state: KeepState) {
    Scaffold(
        topBar = { TopBar(searchTFS = state.searchTFS) },
        bottomBar = { BottomBar() })
    {
        Column {
            EditComponent(
                isSavedKeep = state.currentID.value != null,
                isEditClicked = state.isEditClicked,
                titleTSF = state.titleTFS,
                descriptionTSF = state.descriptionTFS,
                onTitleClick = {
                    state.currentID.value = null
                },
                onClose = {
                    state.updateOrCreateKeep()
                }
            )
            KeepsGridView(state.listOfKeep, onPreviewClick = {
                state.loadKeep(it)
            })
        }
    }
}

@Composable
fun EditComponent(
    isEditClicked: MutableState<Boolean>,
    titleTSF: MutableState<TextFieldValue>,
    descriptionTSF: MutableState<TextFieldValue>,
    isSavedKeep: Boolean,
    onTitleClick: () -> Unit,
    onClose: () -> Unit
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
                        horizontalArrangement = Arrangement.End
                    ) {
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


@Composable
fun TopBar(
    searchTFS: MutableState<TextFieldValue>
) {
    val isOpen: MutableState<Boolean> = remember { mutableStateOf(false) }
    val fr = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = isOpen.value)
    {
        if (isOpen.value) {
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

                if (!isOpen.value) {
                    Text(
                        text = "Keeps", style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onPrimary,
                        letterSpacing = 1.sp
                    )
                } else {
                    TextField(
                        value = searchTFS.value,
                        onValueChange = { searchTFS.value = it },
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
                        if (!isOpen.value)
                            searchTFS.value = TextFieldValue()

                        isOpen.value = !isOpen.value
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

@Composable
fun BottomBar() {
    BottomAppBar(backgroundColor = MaterialTheme.colors.secondaryVariant) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .clip(CircleShape)
                .weight(0.1f),
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .clip(CircleShape)
                .weight(0.1f),
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .clip(CircleShape)
                .weight(0.1f),
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KeepsGridView(listOfKeep: MutableList<KeepNotes>, onPreviewClick: (KeepNotes) -> Unit) {
    if (listOfKeep.size > 0) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            items(listOfKeep.size)
            {
                KeepPreviewCard(listOfKeep[it], onClick = { onPreviewClick.invoke(it) })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KeepPreviewCard(keep: KeepNotes, onClick: (KeepNotes) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp,
        modifier = Modifier.padding(5.dp),
        onClick = { onClick(keep) }
    )
    {
        Column(modifier = Modifier.padding(10.dp)) {
            if (keep.title.value.isNotBlank())
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = keep.title.value,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(0.8f),
                    )

                    /*IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(CircleShape)
                            .weight(0.1f)
                            .offset(y = (-15).dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            tint = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.3f)
                        )
                    }*/
                }
            Text(
                text = keep.description.value,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}