package it.unibz.myrecipes.ui.navigation.bottomnav

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import it.unibz.myrecipes.viewmodel.FavoritesViewModel
import it.unibz.myrecipes.viewmodel.HomeViewModel

@Composable
fun BottomNavigation(navController: NavController,
                     homeViewModel: HomeViewModel,
                     favoritesViewModel: FavoritesViewModel) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.Favorites,
    )

    NavigationBar (
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.imageVector,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = true,
                onClick = {

                    navController.navigate(item.screenRoute) {
                        // Home screen random recipes change
                        if (item.screenRoute == BottomNavigationItem.Home.screenRoute) {
                            homeViewModel.retrieveRandomMeals()
                        } else if (item.screenRoute == BottomNavigationItem.Favorites.screenRoute) {
                            favoritesViewModel.getAllFavorites()
                        }
                        // Jei spaudžiamas Home/Search/Favorites, išvalomas visas BackStack
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false // Paliekamas start destination (Home/Search/Favorites)
                        }
                        launchSingleTop = true // Vengiamas dublikavimas ekrano
                        restoreState = true // Atkuriama būsena
                    }
                }
            )
        }
    }
}