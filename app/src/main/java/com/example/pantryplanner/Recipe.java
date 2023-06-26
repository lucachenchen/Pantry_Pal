package com.example.pantryplanner;

import java.util.List;

public class Recipe {
    private int id;
    private String recipeName;
    private String iconName;
    private String preparationTime;
    private List<String> ingredients;
    private List<String> preparationSteps;

    private List<Grocery> ingredientsAvailable;

    public Recipe() {}

    public Recipe(int id, String recipeName, String iconName, String preparationTime, List<String> ingredients, List<String> preparationSteps) {
        this.id = id;
        this.recipeName = recipeName;
        this.iconName = iconName;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(List<String> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public List<Grocery> getIngredientsAvailable() {
        return ingredientsAvailable;
    }

    public void setIngredientsAvailable(List<Grocery> ingredientsAvailable) {
        this.ingredientsAvailable = ingredientsAvailable;
    }
}

