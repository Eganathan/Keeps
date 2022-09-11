package online.onenut.keeps.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DrawerComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(
            text = AnnotatedString(text = "made in ").plus(
                AnnotatedString(
                    "India",
                    spanStyle = SpanStyle(
                        color = Color(0xFF4CBB17),
                        fontWeight = FontWeight.ExtraBold
                    )
                ).plus(
                    AnnotatedString(" with ").plus(
                        AnnotatedString(
                            text = "love ",
                            spanStyle = SpanStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.ExtraBold
                            )
                        ).plus(
                            AnnotatedString(text = "by")
                        )
                    )
                )
            ),
            style = TextStyle(
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp
            )
        )
        Text(
            "OneNut-Studio",
            style = TextStyle(
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                letterSpacing = 1.sp
            )
        )
    }
}