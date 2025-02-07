package it.unibz.myrecipes.ui.screen

import NavigationGraph
import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import it.unibz.myrecipes.ui.navigation.bottomnav.BottomNavigation
import it.unibz.myrecipes.viewmodel.FavoritesViewModel
import it.unibz.myrecipes.viewmodel.HomeViewModel
import it.unibz.myrecipes.viewmodel.MealDetailsViewModel
import it.unibz.myrecipes.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(homeViewModel: HomeViewModel,
               searchViewModel: SearchViewModel,
               mealDetailsViewModel: MealDetailsViewModel,
               favoritesViewModel: FavoritesViewModel
){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController,
            homeViewModel,
            favoritesViewModel) }
    ) {
        NavigationGraph(
            navController = navController,
            homeViewModel = homeViewModel,
            searchViewModel = searchViewModel,
            mealDetailsViewModel = mealDetailsViewModel,
            favoritesViewModel = favoritesViewModel)
    }
}