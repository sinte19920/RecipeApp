package it.unibz.myrecipes.data.randommeal

import com.squareup.moshi.Json

data class MoshiRandomMeal(
    val idMeal: String,
    val strMeal: String,
    @Json(name = "strMealThumb") val strMealThumb: String
)

fun MoshiRandomMeal.asRandomMeal(): RandomMeal {
    return RandomMeal (
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb
    )
}