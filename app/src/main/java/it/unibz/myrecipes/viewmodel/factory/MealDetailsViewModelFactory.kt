package it.unibz.myrecipes.viewmodel.factory

import androidx.lifecycle.ViewModel
import it.unibz.myrecipes.repo.InternalRepository
import it.unibz.myrecipes.viewmodel.MealDetailsViewModel

class MealDetailsViewModelFactory(
    private val repository: InternalRepository
) : AbstractViewModelFactory<MealDetailsViewModel>(MealDetailsViewModel::class.java) {
    override fun createViewModel(): ViewModel {
        return MealDetailsViewModel(repository)
    }
}