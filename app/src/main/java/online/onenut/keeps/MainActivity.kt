package online.onenut.keeps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import online.onenut.keeps.ui.theme.KeepsTheme
import online.onenut.keeps.views.App
import online.onenut.keeps.views.KeepState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.secondaryVariant
                ) {
                    val scope = rememberCoroutineScope()
                    val state = remember {
                        KeepState(scope.coroutineContext)
                    }
                    App(state)
                }
            }
        }
    }
}

