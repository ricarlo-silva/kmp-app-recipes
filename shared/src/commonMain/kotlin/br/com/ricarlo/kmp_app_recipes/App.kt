package br.com.ricarlo.kmp_app_recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import br.com.ricarlo.designsystem.MyApplicationTheme
import br.com.ricarlo.designsystem.system
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MyApplicationTheme {
        Column {
            Text("Hello, ${getPlatform().name}!")
            Text("Hello, ${system()}!")
        }
    }
}
