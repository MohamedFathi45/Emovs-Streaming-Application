package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.emovs.Loader_Tasks.ActorWorksLoader;

import java.util.ArrayList;

import static com.example.emovs.MainActivity.LOADER_FOR_LOAD_ACTOR_WORKS;
import static com.example.emovs.MainActivity.SERVER_LINK;

public class ActorWorks extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>{


    ArrayList<com.example.emovs.GeneralShow> ret = null;
    String id;
    GridView actor_works_grid_view;
    LinearLayout progress_bar_linear_layout;
    LinearLayout resultsLayout;
    LinearLayout noResultsLayout;
    public static final String SERVER_LOAD_ACTOR_WORKS_API = MainActivity.SERVER_ROOT_LINK_URL+"/actors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_works);
        actor_works_grid_view = findViewById(R.id.id_actor_works);
        progress_bar_linear_layout = findViewById(R.id.id_progress_bar_linear_layout);
        resultsLayout = findViewById(R.id.id_results_layout);
        noResultsLayout = findViewById(R.id.id_no_results_layout);
        Intent intent = getIntent();
        id = intent.getStringExtra(MainActivity.ID);
        String name = intent.getStringExtra(MainActivity.NAME);
        setTitle(name);
        getActorWorks(id);


        actor_works_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(ret.get(i).getCat_id() == 2) {
                    Intent intent = new Intent(ActorWorks.this, MovieDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(MainActivity.MOVIE_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(ActorWorks.this , SeriesDetailsActivity.class);
                    com.example.emovs.Inventory.getInstance().add(ret.get(i));
                    intent.putExtra(MainActivity.TV_SHOW_OBJECT_ID , String.valueOf(ret.get(i).getId()));
                    startActivity(intent);
                }
            }
        });


    }


    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == LOADER_FOR_LOAD_ACTOR_WORKS){
            loader = new ActorWorksLoader(this , args.getString(SERVER_LINK) ,args.getString(MainActivity.ID) , resultsLayout , noResultsLayout , progress_bar_linear_layout);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if( id == MainActivity.LOADER_FOR_LOAD_ACTOR_WORKS){
                if(data != null) {
                    ret = (ArrayList<com.example.emovs.GeneralShow>) data;
                    progress_bar_linear_layout.setVisibility(View.GONE);
                    resultsLayout.setVisibility(View.VISIBLE);
                    noResultsLayout.setVisibility(View.GONE);
                    com.example.emovs.SearchResultAdapterGridView works = new com.example.emovs.SearchResultAdapterGridView(this, (ArrayList<com.example.emovs.GeneralShow>) data);
                    actor_works_grid_view.setAdapter(works);
                }
                else{
                    progress_bar_linear_layout.setVisibility(View.GONE);
                    resultsLayout.setVisibility(View.GONE);
                    noResultsLayout.setVisibility(View.VISIBLE);
                }
            }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }


    public void getActorWorks(String actorId){
        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(MainActivity.SERVER_LINK, SERVER_LOAD_ACTOR_WORKS_API+"/"+actorId+"/works");
        queryBundle.putString(MainActivity.ID , actorId);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_FOR_LOAD_ACTOR_WORKS, queryBundle, this);
    }
}
