package it.unibz.myrecipes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.repo.InternalRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private var repository: InternalRepository,
) : ViewModel() {

    var favoriteMeals: List<Meal>? by mutableStateOf(null)
        private set

    init {
        getAllFavorites()
    }

    fun getAllFavorites() = viewModelScope.launch {
        favoriteMeals = repository.getMeals()
    }

    // Funkcija, kuri atnaujina mėgstamų receptų sąrašą
    fun refreshFavorites() {
        getAllFavorites()
    }
}
