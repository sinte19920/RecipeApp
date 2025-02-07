package it.unibz.myrecipes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.viewmodel.SearchViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import it.unibz.myrecipes.ui.navigation.CustomTopBar
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onItemClick: (Meal) -> Unit
) {
    // Update stateflow changes
    val searchText by viewModel.searchText.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    // State for showing the no results message after a delay
    var showNoResultsMessage by remember { mutableStateOf(false) }

    // Delay the display of the no results message
    LaunchedEffect(isSearching, recipes) {
        if (!isSearching && recipes.isEmpty() && searchText.isNotEmpty()) {
            delay(500)
            showNoResultsMessage = true
        } else {
            showNoResultsMessage = false
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Search Recipes"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            // Search bar
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = { Text(text = "Enter recipe's name...") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Loader and results
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                // Show message if no recipe found and search has completed after delay
                if (showNoResultsMessage && searchText.isNotEmpty()) {
                    Text(
                        text = "Sorry, we don't have this recipe yet. Try another one!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(bottom = 95.dp)
                    ) {
                        items(recipes) { recipe ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable(onClick = { onItemClick(recipe) }),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(recipe.imageUrl) // URL recipe's picture
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = recipe.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RectangleShape)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = recipe.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = recipe.category ?: "Unknown category",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
