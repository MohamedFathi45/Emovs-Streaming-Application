package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.emovs.Loader_Tasks.OurWorksLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class OurWorks extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>{

    public static final String SERVER_LOAD_OUR_WORKS_API= com.example.emovs.MainActivity.SERVER_ROOT_LINK_URL+"/emovs_rest/api/read_our_works.php";
    ArrayList<com.example.emovs.GeneralShow> ret = null;
    GridView mOurWorksGridView;
    LinearLayout resultsLayout;
    LinearLayout loadingLayout;
    LinearLayout noResultsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_works);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mOurWorksGridView = findViewById(R.id.id_our_works_grid);
        resultsLayout = findViewById(R.id.id_results_layout_our_works);
        loadingLayout= findViewById(R.id.id_progress_bar_linear_layout_our_works);
        noResultsLayout = findViewById(R.id.id_no_results_layout_our_works);



        BottomNavigationView mBottonNavigationView = (BottomNavigationView) findViewById(R.id.id_bottom_navigation);
        //if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
        //mBottonNavigationView.setItemIconTintList(null);
        mBottonNavigationView.setSelectedItemId(R.id.id_nav_our_works);
        mBottonNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_nav_search:
                        Intent intent = new Intent(OurWorks.this, Search.class);
                        startActivity(intent);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_our_works:
                        return true;
                    case R.id.id_nav_more:
                        Intent intent1 = new Intent(OurWorks.this, com.example.emovs.More.class);
                        startActivity(intent1);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_home:
                        Intent intent2 = new Intent(OurWorks.this, com.example.emovs.MainActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
        getOurWorksFromServer();

        mOurWorksGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(ret.get(i).getCat_id() == 2) {
                    Intent intent = new Intent(OurWorks.this, com.example.emovs.MovieDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(com.example.emovs.MainActivity.MOVIE_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(OurWorks.this , com.example.emovs.SeriesDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(com.example.emovs.MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == com.example.emovs.MainActivity.LOADER_FOR_LOAD_OUR_WORKS){
            loader = new OurWorksLoader(this , args.getString(com.example.emovs.MainActivity.SERVER_LINK)  , noResultsLayout , resultsLayout , loadingLayout);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == com.example.emovs.MainActivity.LOADER_FOR_LOAD_OUR_WORKS) {
            if(data != null) {
                noResultsLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                resultsLayout.setVisibility(View.VISIBLE);
                ret = (ArrayList<com.example.emovs.GeneralShow>) data;
                com.example.emovs.SearchResultAdapterGridView resultAdapter = new com.example.emovs.SearchResultAdapterGridView(this, (ArrayList<com.example.emovs.GeneralShow>) data);
                mOurWorksGridView.setAdapter(resultAdapter);
            }
            else{
                loadingLayout.setVisibility(View.GONE);
                resultsLayout.setVisibility(View.GONE);
                noResultsLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }



    private void getOurWorksFromServer(){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(com.example.emovs.MainActivity.SERVER_LINK , SERVER_LOAD_OUR_WORKS_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(com.example.emovs.MainActivity.LOADER_FOR_LOAD_OUR_WORKS, queryBundle, this);

    }
}
