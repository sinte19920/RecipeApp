package it.unibz.myrecipes.repo

import it.unibz.myrecipes.data.meal.Meal
import it.unibz.myrecipes.data.meal.MoshiMealResponse
import it.unibz.myrecipes.data.meal.asMeals
import it.unibz.myrecipes.remote.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(
    private val webService: WebService,
) {

    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }

    suspend fun searchMeal(mealName: String): MoshiMealResponse?
            = withContext(Dispatchers.IO) {

        webService.search(mealName)
        return@withContext webService.search(mealName)
    }

    suspend fun getRandomMeals(count: Int = 4): List<Meal>
            = withContext(Dispatchers.IO) {

        val uniqueMeals = mutableSetOf<Meal>()

        while (uniqueMeals.size < count) {
            try {
                val randomMeal = webService.getRandomMeal().asMeals()?.firstOrNull()
                if (randomMeal != null && !uniqueMeals.contains(randomMeal)) {
                    uniqueMeals.add(randomMeal)
                }
            } catch (e: Exception) {
                // Optional: log the error or handle as needed
                continue
            }
        }

        uniqueMeals.toList()
        return@withContext uniqueMeals.toList()
    }

}