package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MovieDetailsActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    public static final String VIDEO_URL = "vedio_url";
    public static final String SERVER_LOAD_TAGS_API = com.example.emovs.MainActivity.SERVER_ROOT_LINK_URL+"/tags";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tabLayout = findViewById(R.id.id_movie_details_tab_layout);
        viewPager = findViewById(R.id.movie_details_view_pager);
        com.example.emovs.ViewPagerAdapter adapter = new com.example.emovs.ViewPagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Intent intent = getIntent();
        String id = intent.getStringExtra(com.example.emovs.MainActivity.MOVIE_OBJECT_ID);
        adapter.addFragment(new com.example.emovs.FragmentDetails(id) , getString(R.string.details));
        adapter.addFragment(new com.example.emovs.FragmentWatch(id) , getString(R.string.watch));
        adapter.addFragment(new com.example.emovs.FragmentReviews(id) , getString(R.string.reviews));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
