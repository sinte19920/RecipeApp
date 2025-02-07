package it.unibz.myrecipes.ui.navigation.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(var title: String, var imageVector: ImageVector, var screenRoute : String){

    data object Search : BottomNavigationItem("Search", Icons.Filled.Search,"search")
    data object Home: BottomNavigationItem("Home", Icons.Filled.Home,"home")
    data object Favorites: BottomNavigationItem("Favorites", Icons.Filled.Favorite,"favorites")
}