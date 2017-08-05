package ca.mylambton.c0695372.recipesapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;
import android.app.SearchManager;

import ca.mylambton.c0695372.recipesapp.adapters.RecipesAdapter;
import ca.mylambton.c0695372.recipesapp.models.Recipe;
import ca.mylambton.c0695372.recipesapp.models.User;
import ca.mylambton.c0695372.recipesapp.network.ApiClient;
import ca.mylambton.c0695372.recipesapp.network.ApiInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "ca.mylambton.c0695372.recipesapp.mRecipe_";

    Recipe mRecipesList;
    private RecyclerView mRecipesRecyclerView;
    private SearchView mSearchView;

    private static final int REQUEST_USER = 0;

    private SwipeRefreshLayout swipeContainer;

    private User mUser;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RecipesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);


        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setQueryHint("Ingredient");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fetchRecipes("");
                return false;
            }
        });


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchRecipes("");
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        setTitle("Recipes");

        String userEmail = getIntent().getStringExtra(UserActivity.EXTRA_USER);
        if (userEmail != null) {
            Realm realm = Realm.getDefaultInstance();

            mUser = realm.where(User.class).equalTo("email", userEmail).findFirst();
        }


        mRecipesRecyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(mRecipesRecyclerView);


        fetchRecipes("");

    }

    private void fetchRecipes(String ingredient) {
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Recipe> apiServiceData;

        if (ingredient == null || ingredient.isEmpty()) {
            apiServiceData = mApiInterface.getAllRecipes();
        } else {
            apiServiceData = mApiInterface.getRecipesByIngredient(ingredient);
        }

        apiServiceData.enqueue(new Callback<Recipe>()
        {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response)
            {
                mRecipesList = response.body();
                RecipesAdapter mRecipesAdapter = new RecipesAdapter(RecipesActivity.this, mRecipesList.getRecipes());
                mRecipesRecyclerView.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(RecipesActivity.this);
                //llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                //llm.setOrientation(LinearLayoutManager.VERTICAL);
                mRecipesRecyclerView.setLayoutManager(llm);
                //GridLayoutManager gridLayoutManager = new GridLayoutManager(PostsActivity.this, 3);
                //mRecyclerView.setLayoutManager(gridLayoutManager);
                //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                mRecipesRecyclerView.setAdapter(mRecipesAdapter);
//                Log.d(TAG, "Success");
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t)
            {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_recipes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem userProfile = menu.findItem(R.id.edit_profile);
        userProfile.setVisible(true);

        MenuItem favorites = menu.findItem(R.id.favorites);
        favorites.setVisible(true);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.favorites:
                Intent favoriteIntent = FavoritesActivity.newIntent(this);
                startActivityForResult(favoriteIntent, REQUEST_USER);

                break;

            case R.id.edit_profile:
                Intent userIntent = UserActivity.newIntent(this);
                if (mUser != null) {
                    userIntent.putExtra(UserActivity.EXTRA_USER, mUser.getEmail());
                }
                startActivityForResult(userIntent, REQUEST_USER);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                mUser = (User) data.getSerializableExtra("user");
            }
        }
    }

}
