
package ca.mylambton.c0695372.recipesapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("recipes")
    @Expose
    private List<Recipe_> recipes = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipe_> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe_> recipes) {
        this.recipes = recipes;
    }

}
