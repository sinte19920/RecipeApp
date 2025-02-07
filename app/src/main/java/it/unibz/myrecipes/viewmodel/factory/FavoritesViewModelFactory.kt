package it.unibz.myrecipes.viewmodel.factory

import androidx.lifecycle.ViewModel
import it.unibz.myrecipes.repo.InternalRepository
import it.unibz.myrecipes.viewmodel.FavoritesViewModel

class FavoritesViewModelFactory(
    private val repository: InternalRepository
) : AbstractViewModelFactory<FavoritesViewModel>(FavoritesViewModel::class.java) {
    override fun createViewModel(): ViewModel {
        return FavoritesViewModel(repository)
    }
}