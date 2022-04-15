package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Search extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>{

    SearchView mSearchView;
    LinearLayout mLoadingLayout;
    LinearLayout results_layout;
    LinearLayout noResultsLayout;
    GridView searchResultsGridView;
    ArrayList<com.example.emovs.GeneralShow> ret = null;
    public static final String SERVER_SEARCH_API= com.example.emovs.MainActivity.SERVER_ROOT_LINK_URL+"/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_search);
        mSearchView = findViewById(R.id.id_search_bar);
        mLoadingLayout = findViewById(R.id.id_progress_bar_linear_layout);
        results_layout = findViewById(R.id.id_results_layout);
        noResultsLayout = findViewById(R.id.id_no_result_layout);
        searchResultsGridView = findViewById(R.id.id_search_result_grid);

        mLoadingLayout.setVisibility(View.GONE);
        noResultsLayout.setVisibility(View.GONE);
        BottomNavigationView mBottonNavigationView = (BottomNavigationView) findViewById(R.id.id_bottom_navigation);
        //if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
        //mBottonNavigationView.setItemIconTintList(null);
        mBottonNavigationView.setSelectedItemId(R.id.id_nav_search);
        mBottonNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_nav_search:
                        return true;
                    case R.id.id_nav_our_works:
                        Intent intent = new Intent(Search.this, com.example.emovs.OurWorks.class);
                        startActivity(intent);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_more:
                        Intent intent1 = new Intent(Search.this, com.example.emovs.More.class);
                        startActivity(intent1);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;
                    case R.id.id_nav_home:
                        Intent intent2 = new Intent(Search.this, com.example.emovs.MainActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0 , 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mLoadingLayout.setVisibility(View.VISIBLE);
                noResultsLayout.setVisibility(View.GONE);
                results_layout.setVisibility(View.GONE);
               getSearchResultsFromServer(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchResultsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(ret.get(i).getCat_id() == 2) {
                    Intent intent = new Intent(Search.this, com.example.emovs.MovieDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(com.example.emovs.MainActivity.MOVIE_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(Search.this , com.example.emovs.SeriesDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(com.example.emovs.MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
            }
        });
    }



    private void getSearchResultsFromServer(String name){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(com.example.emovs.MainActivity.NAME , name);

        queryBundle.putString(com.example.emovs.MainActivity.SERVER_LINK , SERVER_SEARCH_API+"/"+name );
        Log.d("APPDEBUG" , name);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        //  Get our Loader by calling getLoader and passing the ID we specified
        Loader<Object> search = loaderManager.getLoader(com.example.emovs.MainActivity.LOADER_FOR_SEARCH);
        //  If the Loader was null, initialize it. Else, restart it.
        if (search == null) {
            loaderManager.initLoader(com.example.emovs.MainActivity.LOADER_FOR_SEARCH, queryBundle, this);
        } else {
            loaderManager.restartLoader(com.example.emovs.MainActivity.LOADER_FOR_SEARCH, queryBundle, this);
        }
    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Object>(this) {

            @Override
            protected void onStartLoading() {

                forceLoad();
            }

            @Nullable
            @Override
            public Object loadInBackground() {
                if(id == com.example.emovs.MainActivity.LOADER_FOR_SEARCH) {
                    try {
                        String link = args.getString(com.example.emovs.MainActivity.SERVER_LINK);
                        ArrayList<String> params = new ArrayList<>();
                        params.add(String.valueOf(id));
                        params.add(link);
                        params.add(args.getString(com.example.emovs.MainActivity.NAME));
                        ret = (ArrayList<com.example.emovs.GeneralShow>) com.example.emovs.NetworkOP.executeNetworkOperation(params);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return ret;
                }
                else{
                    return new ArrayList<String>();
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {

        int id = loader.getId();
        if(id == com.example.emovs.MainActivity.LOADER_FOR_SEARCH) {
            if(data != null) {
                mLoadingLayout.setVisibility(View.GONE);
                noResultsLayout.setVisibility(View.GONE);
                results_layout.setVisibility(View.VISIBLE);
                com.example.emovs.SearchResultAdapterGridView resultAdapter = new com.example.emovs.SearchResultAdapterGridView(this, (ArrayList<com.example.emovs.GeneralShow>) data);
                searchResultsGridView.setAdapter(resultAdapter);
            }
            else{
                mLoadingLayout.setVisibility(View.GONE);
                noResultsLayout.setVisibility(View.VISIBLE);
                results_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }
}
