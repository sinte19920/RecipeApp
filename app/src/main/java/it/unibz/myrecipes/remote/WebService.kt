package it.unibz.myrecipes.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.unibz.myrecipes.data.meal.MoshiMealResponse
import it.unibz.myrecipes.repo.MainRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class WebService {
    private val api: MealsApi by lazy {
        createMealsApi()
    }

    suspend fun getRandomMeal(): MoshiMealResponse {
        return api.getRandomMeal()
    }

    suspend fun search(mealName: String): MoshiMealResponse {
        return api.searchMealsByName(mealName)
    }

    private fun createMealsApi(): MealsApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainRepository.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(MealsApi::class.java)
    }
}