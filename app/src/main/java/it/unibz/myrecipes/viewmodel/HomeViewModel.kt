package it.unibz.myrecipes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.repo.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private var repository: MainRepository
) : ViewModel() {

    var fourRandomMeals: List<Meal>? by mutableStateOf(null)
        private set

    init {
        retrieveRandomMeals()
    }

    fun retrieveRandomMeals() = viewModelScope.launch {
        fourRandomMeals = repository.getRandomMeals()
    }
}