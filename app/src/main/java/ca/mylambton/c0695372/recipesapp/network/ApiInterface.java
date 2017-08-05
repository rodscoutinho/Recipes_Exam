package ca.mylambton.c0695372.recipesapp.network;

import ca.mylambton.c0695372.recipesapp.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by moxdroid on 2017-07-31.
 */

public interface ApiInterface {

    @GET("/api/search?key=9d5eca20a103462c1fc2c3653ac6072a")
    Call<Recipe> getAllRecipes();

    @GET("/api/search?key=9d5eca20a103462c1fc2c3653ac6072a")
    Call<Recipe> getRecipesByIngredient(@Query("q") String search_criteria);

    @GET("/api/get?key=9d5eca20a103462c1fc2c3653ac6072a")
    Call<ca.mylambton.c0695372.recipesapp.models.details.RecipeDetail> getRecipeDetailsById(@Query("rId") String rId);

}
