package online.onenut.keeps.views

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

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