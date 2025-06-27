package br.com.ricarlo.cmp_app_recipes.presentation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.savedstate.read
import br.com.ricarlo.common.EventBus
import br.com.ricarlo.designsystem.MyApplicationTheme
import br.com.ricarlo.login.presentation.LoginScreen
import br.com.ricarlo.shared.BuildConfig
import kotlinx.coroutines.flow.filterIsInstance
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipesApp() {

    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        EventBus.events.filterIsInstance<NavUri>().collect { event ->
            navController.navigate("home") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "login",
                enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
            ) {
                composable(
                    route = "home",
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "myapp://${BuildConfig.APPLICATION_ID}/home"
                    })
                ) {
                    HomeScreen(
                        navController = navController
                    )
                }
                composable("login") {
                    LoginScreen(
                        navController = navController
                    )
                }
                composable(
                    route = "details/{id}",
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "myapp://${BuildConfig.APPLICATION_ID}/details/{id}"
                    })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.read { getStringOrNull("id") }
                    Text("ID $id")
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
