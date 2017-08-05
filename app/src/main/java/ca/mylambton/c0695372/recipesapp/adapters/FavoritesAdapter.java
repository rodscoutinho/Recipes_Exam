package ca.mylambton.c0695372.recipesapp.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ca.mylambton.c0695372.recipesapp.R;
import ca.mylambton.c0695372.recipesapp.models.Favorite;

/**
 * Created by rodrigocoutinho on 2017-08-04.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private List<Favorite> mRecipesArrayList;
    private Activity context;

    public FavoritesAdapter(Activity context, List<Favorite> mRecipesArrayList)
    {
        this.mRecipesArrayList = mRecipesArrayList;
        this.context = context;
        this.mRecipesArrayList = mRecipesArrayList;
    }

    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_recipe, parent, false);

        return new RecipesAdapter.RecipesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.RecipesViewHolder holder, int position) {
        Favorite mRecipe = mRecipesArrayList.get(position);
        holder.mTextViewTitle.setText(mRecipe.getTitle());
        Glide.with(context).load(mRecipe.getImageUrl()).thumbnail(1).into(holder.imageViewAlbum);
//        holder.bind(context, recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipesArrayList.size();
    }



    static class FavoritesViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewAlbum;
        TextView mTextViewTitle;
        CardView cvRecipe;
        private Favorite mRecipe;
        private Activity context;

        FavoritesViewHolder(View view)
        {
            super(view);
            imageViewAlbum = (ImageView) view.findViewById(R.id.imgRecipe);
            mTextViewTitle = (TextView) view.findViewById(R.id.txtTitle);
            mTextViewTitle.setSelected(true);
        }

        public void bind(Activity context, Favorite recipe) {
            this.context = context;
            mRecipe = recipe;
        }

    }

}
