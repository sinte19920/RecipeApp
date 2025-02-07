# MyRecipes App
### 2024/2025 Engineering of Mobile Systems

## Overview
**MyRecipes** is a Kotlin-based Android application built using Jetpack Compose. The app allows to explore recipes fetched from [TheMealDB API](https://www.themealdb.com), save favorite ones, and browse or search for recipes.

## Features

<img alt="MyRecipes video" height="500" src="MyRecipes_ScreenRecord.mp4" width="200"/>

Once the app is opened, **Home page** and **Bottom Navigation bar** can be seen.

1. **Home Screen**:
    - Displays a random selection of recipes.

2. **Recipe Details Screen**:
    - Displays detailed information about a recipe, including:
        - Recipe image.
        - Recipe name
        - Origin
        - Ingredients list.
        - Cooking instructions.
    - Option to save the recipe to the Favorites list.

3. **Favorites Screen**:
    - Displays a list of all saved recipes.
    - Allows users to open recipe details from their saved collection.
    - Saves favorite recipes locally in a JSON file.

4. **Search Bar**:
    - Enables users to search recipes by name.
    - Redirects to a screen displaying the search results.

## Getting Started
To set up and run the app, follow these steps:
1. Download repository from [GitLab](https://gitlab.inf.unibz.it/2324-engineeringofmobilesystems/sintija-bambalaite-meal-app)
2. Open the project in Android Studio.
3. Build the project and run it on your selected device.

## Dependencies

This app uses dependencies like:

* Coil: For efficient image loading and caching.
* Kotlin Coroutines: To manage asynchronous tasks.
* Jetpack Compose: For building a modern UI.
* Retrofit: To handle API integration with TheMealDB.
* Moshi: For JSON parsing and data mapping.
* Hilt: For dependency injection, simplifying the use of ViewModels and other components.
* Navigation Component: To manage app navigation between screens.
* Lifecycle: To manage the lifecycle of components like ViewModels and LiveData.

## How to Use the App

1. Explore Recipes:
- Open the app to view a random selection of recipes on the Home Screen.
2. Search Recipes:
- Use the search bar to find recipes by name.
3. View Recipe Details:
- Tap on any recipe card to view detailed information, including ingredients and instructions.
4. Save Recipes:
- Use the "Favorites" button on the Recipe Details screen to add a recipe to your favorites list.
5. View Favorites:
- Navigate to the Favorites screen via the navigation bar to browse saved recipes.

