package online.onenut.keeps.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import online.onenut.keeps.entity.KeepNotes

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