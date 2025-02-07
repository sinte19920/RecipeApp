import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.ui.navigation.NavigationEvent
import it.unibz.myrecipes.ui.navigation.NavigationHandler
import it.unibz.myrecipes.ui.navigation.Screen
import it.unibz.myrecipes.ui.navigation.bottomnav.BottomNavigationItem
import it.unibz.myrecipes.ui.screen.FavoritesScreen
import it.unibz.myrecipes.ui.screen.RandomMealsScreen
import it.unibz.myrecipes.ui.screen.RecipeDetailsScreen
import it.unibz.myrecipes.ui.screen.SearchScreen
import it.unibz.myrecipes.viewmodel.FavoritesViewModel
import it.unibz.myrecipes.viewmodel.HomeViewModel
import it.unibz.myrecipes.viewmodel.MealDetailsViewModel
import it.unibz.myrecipes.viewmodel.SearchViewModel


@Composable
fun NavigationGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    mealDetailsViewModel: MealDetailsViewModel,
    favoritesViewModel: FavoritesViewModel,
    navigationHandler: NavigationHandler = NavigationHandler()
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.screenRoute
    ) {
        // Bottom Navigation Screens
        bottomNavigationScreens(
            navController,
            navigationHandler,
            searchViewModel,
            homeViewModel,
            favoritesViewModel
        )

        // Recipe Details Screen
        recipeDetailsScreen(
            navController,
            mealDetailsViewModel
        )
    }
}

// Extension function to add bottom navigation screens
private fun NavGraphBuilder.bottomNavigationScreens(
    navController: NavHostController,
    navigationHandler: NavigationHandler,
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    // Search Screen
    composable(BottomNavigationItem.Search.screenRoute) {
        SearchScreen(
            viewModel = searchViewModel,
            onItemClick = { meal ->
                navigationHandler.handleNavigation(
                    NavigationEvent.NavigateToRecipeDetails(meal),
                    navController
                )
            }
        )
    }

    // Home Screen
    composable(BottomNavigationItem.Home.screenRoute) {
        RandomMealsScreen(
            viewModel = homeViewModel,
            onItemClick = { meal ->
                navigationHandler.handleNavigation(
                    NavigationEvent.NavigateToRecipeDetails(meal),
                    navController
                )
            }
        )
    }

    // Favorites Screen
    composable(BottomNavigationItem.Favorites.screenRoute) {
        FavoritesScreen(
            viewModel = favoritesViewModel,
            onItemClick = { meal ->
                navController.currentBackStackEntry?.savedStateHandle?.set("meal", meal)
                navController.navigate(Screen.RecipeDetail.route) {
                    launchSingleTop = true
                }
            }
        )
    }
}

// Extension function to add recipe details screen
private fun NavGraphBuilder.recipeDetailsScreen(
    navController: NavHostController,
    mealDetailsViewModel: MealDetailsViewModel
) {
    composable(Screen.RecipeDetail.route) {
        val meal = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Meal>("meal")

        meal?.let {
            RecipeDetailsScreen(
                viewModel = mealDetailsViewModel,
                meal = it,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}