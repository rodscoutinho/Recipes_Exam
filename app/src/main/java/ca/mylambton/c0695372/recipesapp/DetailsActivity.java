package ca.mylambton.c0695372.recipesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ca.mylambton.c0695372.recipesapp.models.Favorite;
import ca.mylambton.c0695372.recipesapp.models.Recipe_;
import ca.mylambton.c0695372.recipesapp.models.details.Recipe;
import ca.mylambton.c0695372.recipesapp.models.details.RecipeDetail;
import ca.mylambton.c0695372.recipesapp.network.ApiClient;
import ca.mylambton.c0695372.recipesapp.network.ApiInterface;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mPublisher;
    private TextView mUrl;
    private TextView mIngredients;
    private RecipeDetail mRecipesDetails;
    private Button mAddToFavorites;

    private Recipe_ mRecipe_;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, DetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mRecipe_ = (Recipe_) getIntent().getSerializableExtra(RecipesActivity.EXTRA_RECIPE);

        mImageView = (ImageView) findViewById(R.id.imgRecipe);

        mTitle = (TextView) findViewById(R.id.recipeTitle);

        mPublisher = (TextView) findViewById(R.id.recipePublisher);

        mUrl = (TextView) findViewById(R.id.recipeUrl);
        mUrl.setText(mRecipe_.getSourceUrl().toLowerCase());

        mIngredients = (TextView) findViewById(R.id.recipeIngredients);
        mIngredients.setMovementMethod(new ScrollingMovementMethod());

        mAddToFavorites = (Button) findViewById(R.id.addToFavorite);
        mAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Realm realm = Realm.getDefaultInstance();

                final Favorite fav = new Favorite();
                fav.setId(mRecipe_.getRecipeId());
                fav.setImageUrl(mRecipe_.getImageUrl());
                fav.setTitle(mRecipe_.getTitle());

                try {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(fav);
                        }
                    });
                    Toast.makeText(DetailsActivity.this, "Favorite.", Toast.LENGTH_SHORT).show();
                } catch (RealmPrimaryKeyConstraintException e) {
                    Toast.makeText(DetailsActivity.this, "This recipe is already a favorite recipe.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fetchRecipes();
    }

    private void fetchRecipes() {
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RecipeDetail> apiServiceData;
        apiServiceData = mApiInterface.getRecipeDetailsById(mRecipe_.getRecipeId());

        apiServiceData.enqueue(new Callback<RecipeDetail>()
        {
            @Override
            public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response)
            {
                mRecipesDetails = response.body();

                Recipe recipe = mRecipesDetails.getRecipe();
                mTitle.setText(recipe.getTitle());
                mPublisher.setText(recipe.getPublisher());
                Glide.with(DetailsActivity.this).load(recipe.getImageUrl()).thumbnail(1).into(mImageView);

                StringBuilder ingredients = new StringBuilder();
                for (String ingredient: recipe.getIngredients()) {
                    ingredients.append(ingredient + "\n\n");
                }

                mIngredients.setText(ingredients);

            }

            @Override
            public void onFailure(Call<RecipeDetail> call, Throwable t)
            {

            }
        });
    }
}
