package br.com.ricarlo.cmp_app_recipes.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.ricarlo.cmp_app_recipes.presentation.RecipesApp

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipesApp()
        }
    }
}

@Composable
@Preview
internal fun AppPreview() {
    RecipesApp()
}
