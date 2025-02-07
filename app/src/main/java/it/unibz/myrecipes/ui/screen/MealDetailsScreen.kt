package it.unibz.myrecipes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import it.unibz.myrecipes.R
import it.unibz.myrecipes.data.Ingredient
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.ui.navigation.CustomTopBar
import it.unibz.myrecipes.viewmodel.MealDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: MealDetailsViewModel,
    meal: Meal,
    onBackPressed: () -> Unit = {}
) {
    val isFavorite by viewModel.isFavorite.collectAsState()
    var isImageOpen by remember { mutableStateOf(false) } // watching if the img is opened

    LaunchedEffect(meal.id) {
        viewModel.checkIfFavorite(meal.id)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Recipe Details",
                onNavigationClick = onBackPressed,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                // Meal Image
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(meal.imageUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.loading_animation)
                            .build(),
                        contentDescription = meal.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clickable { isImageOpen = true }, // "open" picture
                        contentScale = ContentScale.Crop
                    )
                }

                // Meal Details Header
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = meal.name,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = meal.area,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CategoryChip(
                                category = meal.category,
                                modifier = Modifier.weight(1f)
                            )

                            FavoriteButton(
                                isFavorite = isFavorite,
                                onFavoriteToggle = {
                                    viewModel.manageFavorite(!isFavorite, meal)
                                },
                            )
                        }
                    }
                }

                item {
                    SectionHeader(text = "Ingredients")
                }

                items(meal.ingredients) { ingredient ->
                    IngredientItem(ingredient)
                }

                item {
                    SectionHeader(text = "Instructions")
                }

                item {
                    Text(
                        text = meal.instructions,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // Fullscreen Image Overlay
            if (isImageOpen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                        .clickable { isImageOpen = false }, // Uždaryti nuotrauką
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(meal.imageUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.loading_animation)
                            .build(),
                        contentDescription = meal.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f), // Parodyti pilną nuotrauką su proporcijomis
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(category: String,
                 modifier: Modifier = Modifier) {
    Surface(
        modifier.wrapContentSize(align = Alignment.CenterStart),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp
        )
    )
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (isFavorite)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(onClick = onFavoriteToggle)
            .padding(horizontal = 12.dp, vertical = 6.dp)

    ) {
        Icon(
            imageVector = if (isFavorite)
                Icons.Filled.Favorite
            else
                Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(min(24.dp, LocalConfiguration.current.screenWidthDp.dp * 0.05f))
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.labelMedium,
            color = if (isFavorite)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ingredient Name
        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        // Ingredient Measure
        Text(
            text = ingredient.measure,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

//@Preview
//@Composable
//fun RecDetails(meal: Meal = sampleMeal()) {
//    RecipeDetailsScreen(viewModel(), meal = meal)
//}
//
//fun sampleIngredients() = listOf(
//    Ingredient("Chicken Breast", "500g"),
//    Ingredient("Olive Oil", "2 tbsp"),
//    Ingredient("Salt", "1 tsp"),
//    Ingredient("Black Pepper", "1/2 tsp"),
//    Ingredient("Garlic", "2 cloves")
//)
//
//fun sampleMeal() = Meal(
//    id = "52959",
//    name = "Chicken Fajita Pasta",
//    instructions = """
//        1. Heat olive oil in a large skillet over medium-high heat.
//        2. Add chicken and cook until golden brown, about 6-7 minutes.
//        3. Remove chicken and set aside.
//        4. In the same skillet, sauté bell peppers and onions until softened.
//        5. Add garlic and cook for another minute.
//        6. Return chicken to the skillet and add pasta sauce.
//        7. Simmer for 5-10 minutes until heated through.
//        8. Serve hot, garnished with fresh parsley.
//    """.trimIndent(),
//    imageUrl = "https://www.themealdb.com/images/media/meals/qrqywr1503408801.jpg",
//    category = "Chicken",
//    area = "Mexican",
//    ingredients = sampleIngredients(),
//)