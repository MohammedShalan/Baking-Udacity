package app.com.example.shalan.bakingudacity.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.com.example.shalan.bakingudacity.Model.Recipe;
import app.com.example.shalan.bakingudacity.R;
import app.com.example.shalan.bakingudacity.Utils.OnRecipeClickListener;

/**
 * Created by noura on 07/07/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<Recipe> mRecipeList = new ArrayList<Recipe>();
    private Context mContext;
    OnRecipeClickListener mOnRecipeClickListener;

    public RecipeAdapter(Context mContext, OnRecipeClickListener onRecipeClickListener) {
        this.mContext = mContext;
        mOnRecipeClickListener = onRecipeClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_card_view, parent, false);
        ViewHolder mRecipeViewHolder = new ViewHolder(view);

        return mRecipeViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe mRecipe = mRecipeList.get(position);
        holder.recipe_name.setText(mRecipe.getName());
        holder.ingredients.setText(Integer.toString(mRecipe.getIngredients().size()));
        holder.serving.setText(Integer.toString(mRecipe.getServings()));
        holder.step.setText(Integer.toString(mRecipe.getSteps().size()));
        if (mRecipe.getImage().length() != 0) {
            Glide.with(mContext).load(mRecipe.getImage()).into(holder.recipe_image);
        } else {
            //Nothing to show ;
        }

    }

    @Override
    public int getItemCount() {

        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Recipe mRecipe;
        //@BindView(R.id.recipe_name)
        TextView recipe_name;
        //@BindView(ingredients)
        TextView ingredients;
        //@BindView(R.id.serving)
        TextView serving;
        //@BindView(R.id.steps)
        TextView step;
        ImageView recipe_image;

        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this,itemView) ;
            recipe_name = (TextView) itemView.findViewById(R.id.recipe_name);
            ingredients = (TextView) itemView.findViewById(R.id.ingredients);
            serving = (TextView) itemView.findViewById(R.id.serving);
            step = (TextView) itemView.findViewById(R.id.steps);
            recipe_image = (ImageView) itemView.findViewById(R.id.recipe_image_view);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mOnRecipeClickListener != null) {
                mOnRecipeClickListener.onClick(view, mRecipeList, getAdapterPosition());
            }

        }
    }

    public void setmRecipeList(ArrayList<Recipe> recipeList) {
        this.mRecipeList = recipeList;
    }
}
