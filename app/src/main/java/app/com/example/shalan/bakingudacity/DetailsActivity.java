package app.com.example.shalan.bakingudacity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import app.com.example.shalan.bakingudacity.Fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getResources().getBoolean(R.bool.isTablet)) {
            setContentView(R.layout.details_fragments);
            fragment = new DetailsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.details_fragment_tablet, fragment).commit();
        } else {
            fragment = new DetailsFragment();
            setContentView(R.layout.activity_details);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.details_container, fragment).commit();
        }


    }
}
