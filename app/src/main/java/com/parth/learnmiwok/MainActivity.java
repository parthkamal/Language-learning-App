package com.parth.learnmiwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



        TabLayout tabLayout;
        ViewPager viewPager;
        customFragmentAdapter viewPagerAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            viewPager= findViewById(R.id.view_pager);
            tabLayout = findViewById(R.id.tabs);

            viewPagerAdapter = new customFragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);

            // It is used to join TabLayout with ViewPager.
            tabLayout.setupWithViewPager(viewPager);
        }



        //click on listeners

    }


