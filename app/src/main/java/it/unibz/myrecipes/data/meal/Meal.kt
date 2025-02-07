package it.unibz.myrecipes.data.meal

import it.unibz.myrecipes.data.Ingredient
import java.io.Serializable

data class Meal(
    val id: String,
    val name: String,
    val instructions: String,
    val imageUrl: String,
    val category: String,
    val area: String,
    val ingredients: List<Ingredient>,
) : Serializable
