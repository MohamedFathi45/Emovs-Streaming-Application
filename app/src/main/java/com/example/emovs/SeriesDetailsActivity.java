package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SeriesDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    public static final String IDTWO ="id2";
    public static final String IDTHREE ="id3";
    public static final String IDFOUR ="id4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details);

        tabLayout = findViewById(R.id.id_movie_details_tab_layout);
        viewPager = findViewById(R.id.movie_details_view_pager);
        com.example.emovs.ViewPagerAdapter adapter = new com.example.emovs.ViewPagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.TV_SHOW_OBJECT_ID);
        adapter.addFragment(new com.example.emovs.FragmentDetails(id) , getString(R.string.details));
        adapter.addFragment(new com.example.emovs.FragmentWatchSeries(id) , getString(R.string.watch));
        adapter.addFragment(new com.example.emovs.FragmentReviews(id) , getString(R.string.reviews));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
