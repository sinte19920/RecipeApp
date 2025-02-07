package it.unibz.myrecipes.remote

import it.unibz.myrecipes.data.meal.MoshiMealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    // Random meal endpoint still gives a `meals` list with ONE item
    @GET("random.php")
    suspend fun getRandomMeal(): MoshiMealResponse

    @GET("search.php")
    suspend fun searchMealsByName(
        @Query("s") query: String
    ): MoshiMealResponse
}