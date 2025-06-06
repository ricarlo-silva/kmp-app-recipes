package br.com.ricarlo.cmp_app_recipes.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.ricarlo.designsystem.MyApplicationTheme
import br.com.ricarlo.cmp_app_recipes.presentation.RecipesApp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val permissionState = rememberPermissionState(
                            android.Manifest.permission.POST_NOTIFICATIONS
                        )
                        when {
                            permissionState.status.isGranted -> {
                                RecipesApp()
                            }

                            permissionState.status.shouldShowRationale -> {
                                Text(text = "Notification permission is needed to receive notifications.")
                                Button(onClick = { permissionState.launchPermissionRequest() }) {
                                    Text("Request Permission")
                                }
                            }

                            else -> {
                                Text(text = "Please grant notification permission to proceed.")
                                Button(onClick = { permissionState.launchPermissionRequest() }) {
                                    Text("Request Permission")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    RecipesApp()
}
