package it.unibz.myrecipes.ui.navigation

import androidx.navigation.NavController
import it.unibz.myrecipes.data.meal.Meal

// Sealed class for Navigation Events
sealed class NavigationEvent {
    data class NavigateToRecipeDetails(val meal: Meal) : NavigationEvent()
    data object NavigateBack : NavigationEvent()
}

// Navigation Handler
class NavigationHandler {
    fun handleNavigation(
        event: NavigationEvent,
        navController: NavController
    ) {
        when (event) {
            is NavigationEvent.NavigateToRecipeDetails -> {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("meal", event.meal)
                navController.navigate(Screen.RecipeDetail.route)
            }
            is NavigationEvent.NavigateBack -> {
                navController.navigateUp()
            }
        }
    }
}