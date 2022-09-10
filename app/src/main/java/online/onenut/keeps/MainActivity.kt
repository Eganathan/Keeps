package online.onenut.keeps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import online.onenut.keeps.ui.theme.KeepsTheme
import online.onenut.keeps.views.EditComponent
import online.onenut.keeps.views.KeepState
import online.onenut.keeps.views.KeepsGridView
import online.onenut.keeps.views.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepsTheme {
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
        topBar = {
            TopBar(
                searchTFS = state.searchTFS,
                searchState = state.searchState,
                onSearchTyped = { state.getFilteredList(it) })
        },
        /*bottomBar = { BottomBar() }*/
        floatingActionButton = {
            if (!state.isEditClicked.value)
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        state.isEditClicked.value = true
                        state.restTextFieldValue()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = "",
                    )
                }
        },
    )
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
                },
                onDeleteClicked = { state.deleteKeep() }
            )
            KeepsGridView(
                if (state.searchState.value) state.filterdOfKeep else state.listOfKeep,
                onPreviewClick = {
                    state.loadKeep(it)
                })
        }
    }
}