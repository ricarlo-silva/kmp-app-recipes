package br.com.ricarlo.cmp_app_recipes.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.ricarlo.cmp_app_recipes.presentation.RecipesApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            RecipesApp()
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    RecipesApp()
}
