package it.unibz.myrecipes.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.ui.navigation.CustomTopBar
import it.unibz.myrecipes.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onItemClick: (Meal) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getAllFavorites()  // Atkuriamas favoritų sąrašas, kai grįžtama į ekraną
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "My Favorites"
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .padding(padding)
        ) {
            if (viewModel.favoriteMeals != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp)
                ) {
                    items(items = viewModel.favoriteMeals!!) { meal ->
                        SimpleMealCard(meal = meal,
                            onClick = {
                                onItemClick(meal)
                            })
                    }
                }
            }
        }
    }

}