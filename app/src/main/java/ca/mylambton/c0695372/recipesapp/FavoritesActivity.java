package ca.mylambton.c0695372.recipesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ca.mylambton.c0695372.recipesapp.adapters.FavoritesAdapter;
import ca.mylambton.c0695372.recipesapp.models.Favorite;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView mRecipesRecyclerView;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FavoritesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setTitle("Favorites");

        mRecipesRecyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(mRecipesRecyclerView);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Favorite> favorites = realm.where(Favorite.class).findAll();

        FavoritesAdapter mRecipesAdapter = new FavoritesAdapter(FavoritesActivity.this, favorites);
        mRecipesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(FavoritesActivity.this);

        mRecipesRecyclerView.setLayoutManager(llm);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);

    }
}
