package it.unibz.myrecipes.data.randommeal


data class MoshiRandomMealResponse (
    val meals: List<MoshiRandomMeal>
)

fun MoshiRandomMealResponse.asRandomMeals(): List<RandomMeal> {
    return meals.map {
        it.asRandomMeal()
    }
}