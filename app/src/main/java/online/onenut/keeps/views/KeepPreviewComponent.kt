package online.onenut.keeps.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import online.onenut.keeps.entity.KeepNotes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KeepPreviewCard(keep: KeepNotes, onClick: (KeepNotes) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp,
        modifier = Modifier.padding(5.dp),
        onClick = { onClick(keep) },
        /*backgroundColor = Color(
            Random.nextInt(0, 255),
            Random.nextInt(0, 255),
            Random.nextInt(0, 255)
        ).copy(alpha = 0.1f)*/
    )
    {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
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