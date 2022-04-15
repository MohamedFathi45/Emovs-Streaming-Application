package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ShowsCategoriesActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_categories);
        setTitle(getResources().getString(R.string.categories));
    }



    public void moreRealityShowsClickListner(View view){
        Intent intent = new Intent(this , MoreShowsActivity.class);
        intent.putExtra(MainActivity.TYPE , "reality");
        startActivity(intent);
    }

    public void moreTvDocumentaryClickListner(View view){

        Intent intent = new Intent(this , MoreShowsActivity.class);
        intent.putExtra(MainActivity.TYPE , "documentary");
        startActivity(intent);
    }

    public void moreEntertaimentShowsClickListner(View view){
        Intent intent = new Intent(this , MoreShowsActivity.class);
        intent.putExtra(MainActivity.TYPE , "entertainment");
        startActivity(intent);
    }
    public void moreTvShowsClickListner(View view) {
        Intent intent = new Intent(this , MoreShowsActivity.class);
        intent.putExtra(MainActivity.TYPE , "tv_shows");
        startActivity(intent);
    }

}
