package com.turquoise.hotelbookrecomendation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.turquoise.hotelbookrecomendation.model.Booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CartListener, Serializable {

    private static Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Booking bookings=new Booking();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Hotel App");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("activityresult", "onActivityResult: "+resultCode);
        if(resultCode!=1){
            update(-1);
        }
        else {
            update(1);
        }
    }

    private void setupViewPager(final ViewPager viewPager) {
        final ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new HomeFrag(),"Home");
        viewPagerAdapter.addFrag(new Recommendation(),"Recommendations");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){

                    ((Recommendation)viewPagerAdapter.getItem(position)).updateList();

                }
                else{
                    ((HomeFrag)viewPagerAdapter.getItem(position)).updateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(viewPagerAdapter);
    }
//
//    @Override
//    public void onFragmentInteraction(String s) {
//
//        int cur=Integer.valueOf(((TextView)toolbar.findViewById(R.id.cartCount)).getText().toString());
//        ((TextView)toolbar.findViewById(R.id.cartCount)).setText(String.valueOf(++cur));
//
//    }



    @Override
    public void update(int n) {
        int cur=Integer.valueOf(((TextView)toolbar.findViewById(R.id.cartCount)).getText().toString());
        ((TextView)toolbar.findViewById(R.id.cartCount)).setText(String.valueOf(cur+n));
    }

    public static void updatec(int n) {
        int cur=Integer.valueOf(((TextView)toolbar.findViewById(R.id.cartCount)).getText().toString());
        ((TextView)toolbar.findViewById(R.id.cartCount)).setText(String.valueOf(cur+n));
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
