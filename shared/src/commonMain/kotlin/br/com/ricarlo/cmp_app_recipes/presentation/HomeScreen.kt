package br.com.ricarlo.cmp_app_recipes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.ricarlo.designsystem.system
import br.com.ricarlo.cmp_app_recipes.getPlatform
import br.com.ricarlo.network.Greeting
import br.com.ricarlo.network.utils.logError
import br.com.ricarlo.network.utils.toJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    HomeContent()
}

@Composable
internal fun HomeContent() {
    var greetingText by remember { mutableStateOf("Loading...") }
    LaunchedEffect(Unit) {
        launch {
            greetingText = withContext(Dispatchers.IO) {
                runCatching {
                    Greeting().greeting().toJson()
                }.onFailure {
                    it.logError()
                }.getOrNull().orEmpty()
            }
        }
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text("Hello, ${getPlatform().name}!")
        Text("Hello, ${system()}!")
        Text(greetingText)
    }
}
