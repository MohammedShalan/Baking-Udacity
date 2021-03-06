package app.com.example.shalan.bakingudacity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import app.com.example.shalan.bakingudacity.Adapters.RecipeAdapter;
import app.com.example.shalan.bakingudacity.DetailsActivity;
import app.com.example.shalan.bakingudacity.Model.Recipe;
import app.com.example.shalan.bakingudacity.Network.RecipeAPI;
import app.com.example.shalan.bakingudacity.R;
import app.com.example.shalan.bakingudacity.Utils.OnRecipeClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.com.example.shalan.bakingudacity.MainActivity.mMainActivityIdlingResource;
import static app.com.example.shalan.bakingudacity.Utils.ApiUtils.getRecipes;


public class MainFragment extends Fragment implements OnRecipeClickListener {
    //    @BindView(R.id.recipe_recyclerView)
//    RecyclerView Recipe_recyclerView ;
    RecipeAdapter recipeAdapter;
    CoordinatorLayout coordinatorLayout;
    private RecipeAPI mRecipeAPI;
    ProgressBar progressBar;
    RecyclerView Recipe_recyclerView;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ButterKnife.bind(this);

    }


    @Override
    public void onClick(View view, ArrayList<Recipe> recipeList, int Position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("list", recipeList);
        intent.putExtra("position", Position);
        Log.v("Main", recipeList.get(Position).getIngredients().toString());
        MainFragment.this.startActivity(intent);

    }


    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        Recipe_recyclerView.setVisibility(View.INVISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        Recipe_recyclerView = (RecyclerView) view.findViewById(R.id.recipe_recyclerView);

        recipeAdapter = new RecipeAdapter(getContext(), this);
        if (getResources().getBoolean(R.bool.isTablet)) {
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            Recipe_recyclerView.setLayoutManager(layoutManager);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            Recipe_recyclerView.setLayoutManager(layoutManager);
        }

        mRecipeAPI = getRecipes();
        getRecipe();
        return view;
    }

    public void getRecipe() {

        mRecipeAPI.getRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipeAdapter.setmRecipeList(response.body());
                recipeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                Recipe_recyclerView.setVisibility(View.VISIBLE);
                Recipe_recyclerView.setAdapter(recipeAdapter);

                if (mMainActivityIdlingResource != null) {
                    mMainActivityIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("Main", "Failed!" + t.toString());
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(android.R.id.content), "Check your connection!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getRecipe();
                            }
                        });
                snackbar.show();


            }
        });
    }




}
