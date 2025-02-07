package it.unibz.myrecipes.repo

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.unibz.myrecipes.data.meal.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class InternalRepository(
    private val context: Context
) {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    companion object {
        const val MEALS_FILE_NAME = "meals_collection.json"
    }

    private val mealsType = newParameterizedType(List::class.java, Meal::class.java)
    private val mealsAdapter = moshi.adapter<List<Meal>>(mealsType)

    // Read meals from internal storage
    suspend fun getMeals(): List<Meal> = withContext(Dispatchers.IO) {
        try {
            val file = File(context.filesDir, MEALS_FILE_NAME)
            if (!file.exists()) {
                // Return empty list if file doesn't exist
                return@withContext emptyList()
            }

            val jsonString = file.readText()
            mealsAdapter.fromJson(jsonString) ?: emptyList()

        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addMeal(meal: Meal): Boolean = withContext(Dispatchers.IO) {
        try {
            // Read existing meals
            val existingMeals = getMeals().toMutableList()

            // Check if meal with same ID already exists
            val existingMealIndex = existingMeals.indexOfFirst { it.id == meal.id }

            if (existingMealIndex != -1) {
                existingMeals[existingMealIndex] = meal
            } else {
                existingMeals.add(meal)
            }

            val updatedJsonString = mealsAdapter.toJson(existingMeals)

            File(context.filesDir, MEALS_FILE_NAME).writeText(updatedJsonString)
            true

        } catch (e: Exception) {
            // Log error
            false
        }
    }

    // Remove a meal by ID
    suspend fun removeMeal(mealId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val existingMeals = getMeals().toMutableList()
            val removed = existingMeals.removeIf { it.id == mealId }

            if (removed) {
                // Convert updated list to JSON
                val updatedJsonString = mealsAdapter.toJson(existingMeals)

                File(context.filesDir, MEALS_FILE_NAME).writeText(updatedJsonString)
            }
            removed

        } catch (e: Exception) {
            false
        }
    }

    suspend fun isMealFavorite(mealId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val meals = getMeals()
            meals.any { it.id == mealId }
        } catch (e: Exception) {
            false
        }
    }


}