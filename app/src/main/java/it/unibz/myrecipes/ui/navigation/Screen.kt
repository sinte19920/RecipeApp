package it.unibz.myrecipes.ui.navigation

sealed class Screen(val route: String) {
    data object RecipeDetail : Screen("recipe")
}