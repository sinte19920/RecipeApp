package it.unibz.myrecipes.data.meal


data class MoshiMealResponse(
    val meals: List<MoshiMeal>?
)

fun MoshiMealResponse.asMeals(): List<Meal>? {
    return meals?.map {
        it.asMeal()
    }
}