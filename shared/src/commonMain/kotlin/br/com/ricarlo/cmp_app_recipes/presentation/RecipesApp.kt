package br.com.ricarlo.cmp_app_recipes.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.ricarlo.designsystem.MyApplicationTheme
import br.com.ricarlo.login.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipesApp() {

    val navController = rememberNavController()
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("home") {
                    HomeScreen(
                        navController = navController
                    )
                }
                composable("login") {
                    LoginScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    RecipesApp()
}
