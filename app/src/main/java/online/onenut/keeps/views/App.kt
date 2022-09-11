package online.onenut.keeps.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun App(state: KeepState) {

    Scaffold(
        scaffoldState = ScaffoldState(
            drawerState = state.drawerState,
            snackbarHostState = state.snackbarHostState
        ),
        topBar = {
            TopBar(
                searchTFS = state.searchTFS,
                searchState = state.searchState,
                onSearchTyped = { state.getFilteredList(it) },
                onSettingsClicked = {
                    state.scope.launch {
                        state.searchState.value = false
                        state.isEditClicked.value = false
                        state.drawerState.open()
                    }
                })
        },
        /*bottomBar = { BottomBar() },*/
        drawerContent = {
            DrawerComponent()
        },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondaryVariant.copy(alpha = 0.1f))
        ) {
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