package it.unibz.myrecipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.data.meal.asMeals
import it.unibz.myrecipes.repo.MainRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _recipes = MutableStateFlow(listOf<Meal>())
    val recipes = _searchText
        /*
         * If the text changes and does not change in the next X millis
         * request WILL be executed
         */
        .debounce(timeoutMillis = 500L)
        .onEach { _isSearching.update { true } }
        .combine(_recipes) { searchQuery, recipes ->
            if (searchQuery.isBlank()) {
                recipes
            } else {
                val results  = repository.searchMeal(searchQuery)
                results?.asMeals() ?: emptyList()
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _recipes.value
        )


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

//    fun clearSearchText() {
//        _searchText.value = ""
//    }
}
