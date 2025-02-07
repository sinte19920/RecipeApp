package it.unibz.myrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import it.unibz.myrecipes.remote.WebService
import it.unibz.myrecipes.repo.InternalRepository
import it.unibz.myrecipes.repo.MainRepository
import it.unibz.myrecipes.ui.screen.MainScreen
import it.unibz.myrecipes.ui.theme.MyRecipesTheme
import it.unibz.myrecipes.viewmodel.FavoritesViewModel
import it.unibz.myrecipes.viewmodel.HomeViewModel
import it.unibz.myrecipes.viewmodel.MealDetailsViewModel
import it.unibz.myrecipes.viewmodel.SearchViewModel
import it.unibz.myrecipes.viewmodel.factory.FavoritesViewModelFactory
import it.unibz.myrecipes.viewmodel.factory.HomeViewModelFactory
import it.unibz.myrecipes.viewmodel.factory.MealDetailsViewModelFactory
import it.unibz.myrecipes.viewmodel.factory.SearchViewModelFactory


class MainActivity : ComponentActivity() {
    private lateinit var mainRepository: MainRepository
    private lateinit var internalRepository: InternalRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainRepository = MainRepository(WebService())
        internalRepository = InternalRepository(this)

        setContent {
            MyRecipesTheme {
                // Create ViewModel instances using ViewModelFactory
                val homeViewModel: HomeViewModel =
                    viewModel(factory = HomeViewModelFactory(mainRepository))
                val searchViewModel: SearchViewModel =
                    viewModel(factory = SearchViewModelFactory(mainRepository))
                val mealDetailsViewModel: MealDetailsViewModel =
                    viewModel(factory = MealDetailsViewModelFactory(internalRepository))
                val favoritesViewModel: FavoritesViewModel =
                    viewModel(factory = FavoritesViewModelFactory(internalRepository))

                MainScreen(
                    homeViewModel,
                    searchViewModel,
                    mealDetailsViewModel,
                    favoritesViewModel
                )
            }
        }
    }
}
