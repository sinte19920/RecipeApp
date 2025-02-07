package it.unibz.myrecipes.viewmodel.factory

import androidx.lifecycle.ViewModel
import it.unibz.myrecipes.repo.MainRepository
import it.unibz.myrecipes.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val repository: MainRepository
) : AbstractViewModelFactory<HomeViewModel>(HomeViewModel::class.java) {
    override fun createViewModel(): ViewModel {
        return HomeViewModel(repository)
    }
}