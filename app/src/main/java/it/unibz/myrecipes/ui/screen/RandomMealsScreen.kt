package it.unibz.myrecipes.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.ui.navigation.CustomTopBar
import it.unibz.myrecipes.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomMealsScreen(viewModel: HomeViewModel,
                      onItemClick: (Meal) -> Unit) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Discover New Recipes!"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            if (viewModel.fourRandomMeals != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = 80.dp)
                ) {
                    items(items = viewModel.fourRandomMeals!!) { meal ->
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