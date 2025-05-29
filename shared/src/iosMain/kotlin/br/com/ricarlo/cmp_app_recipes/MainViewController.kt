package br.com.ricarlo.cmp_app_recipes

import androidx.compose.ui.window.ComposeUIViewController
import br.com.ricarlo.cmp_app_recipes.presentation.RecipesApp
import br.com.ricarlo.cmp_app_recipes.presentation.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
//        initKoin {  }
    }
) { RecipesApp() }
