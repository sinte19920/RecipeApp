package it.unibz.myrecipes.data

import java.io.Serializable

data class Ingredient(
    val name: String,
    val measure: String
) : Serializable