package it.unibz.myrecipes.viewmodel.factory

import androidx.lifecycle.ViewModel
import it.unibz.myrecipes.repo.MainRepository
import it.unibz.myrecipes.viewmodel.SearchViewModel

class SearchViewModelFactory(
    private val repository: MainRepository
) : AbstractViewModelFactory<SearchViewModel>(SearchViewModel::class.java) {
    override fun createViewModel(): ViewModel {
        return SearchViewModel(repository)
    }
}