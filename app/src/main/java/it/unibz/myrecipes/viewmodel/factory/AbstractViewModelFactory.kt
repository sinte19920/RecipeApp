package it.unibz.myrecipes.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// https://stackoverflow.com/questions/74233807/data-is-lost-after-rotation-on-jetpack-compose-with-navigation
// https://www.answertopia.com/jetpack-compose/a-jetpack-compose-room-database-and-repository-tutorial/
abstract class AbstractViewModelFactory<T : ViewModel>(
    private val modelClass: Class<T>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(this.modelClass)) {
            return createViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    protected abstract fun createViewModel(): ViewModel

}