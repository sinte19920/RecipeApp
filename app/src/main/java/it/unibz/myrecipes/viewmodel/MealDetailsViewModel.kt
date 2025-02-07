package it.unibz.myrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.repo.InternalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private var repository: InternalRepository,
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun checkIfFavorite(mealId: String) = viewModelScope.launch {
        val favorite = repository.isMealFavorite(mealId)
        _isFavorite.update { favorite }
    }

    private fun addToFavorites(meal: Meal) = viewModelScope.launch {
        _isFavorite.update { true }
        repository.addMeal(meal)
    }

    private fun removeFromFavorites(mealId: String) = viewModelScope.launch {
        _isFavorite.update { false }
        repository.removeMeal(mealId)
    }

    fun manageFavorite(favorite: Boolean, meal: Meal) {
        if (favorite) {
            addToFavorites(meal)
        } else {
            removeFromFavorites(meal.id)
        }
    }

}