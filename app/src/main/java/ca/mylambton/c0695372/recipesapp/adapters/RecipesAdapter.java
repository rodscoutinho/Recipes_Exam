package ca.mylambton.c0695372.recipesapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ca.mylambton.c0695372.recipesapp.DetailsActivity;
import ca.mylambton.c0695372.recipesapp.R;
import ca.mylambton.c0695372.recipesapp.RecipesActivity;
import ca.mylambton.c0695372.recipesapp.models.Recipe_;

/**
 * Created by moxdroid on 2017-07-31.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{

    private List<Recipe_> mRecipesArrayList;
    private Activity context;

    public RecipesAdapter(Activity context, List<Recipe_> mRecipesArrayList)
    {
        this.mRecipesArrayList = mRecipesArrayList;
        this.context = context;
        this.mRecipesArrayList = mRecipesArrayList;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_recipe, parent, false);

        return new RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        Recipe_ mRecipe = mRecipesArrayList.get(position);
        holder.mTextViewTitle.setText(mRecipe.getTitle());
        Glide.with(context).load(mRecipe.getImageUrl()).thumbnail(1).into(holder.imageViewAlbum);
        Recipe_ recipe = mRecipesArrayList.get(position);
        holder.bind(context, recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipesArrayList.size();
    }



    static class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageViewAlbum;
        TextView mTextViewTitle;
        CardView cvRecipe;
        private Recipe_ mRecipe;
        private Activity context;

        RecipesViewHolder(View view)
        {
            super(view);
            imageViewAlbum = (ImageView) view.findViewById(R.id.imgRecipe);
            mTextViewTitle = (TextView) view.findViewById(R.id.txtTitle);
            mTextViewTitle.setSelected(true);

            cvRecipe = (CardView) view.findViewById(R.id.cvRecipe);
            cvRecipe.setOnClickListener(this);
        }

        public void bind(Activity context, Recipe_ recipe) {
            this.context = context;
            mRecipe = recipe;
        }

        @Override
        public void onClick(View view) {
            Intent intent = DetailsActivity.newIntent(context);
            intent.putExtra(RecipesActivity.EXTRA_RECIPE, mRecipe);
            context.startActivity(intent);
        }
    }
}