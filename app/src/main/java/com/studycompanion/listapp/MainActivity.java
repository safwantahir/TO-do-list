package com.studycompanion.listapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    ViewPagerFragmentAdapter adapter;

    // array for tab labels
    private String[] labels = new String[]{"Urgent Tasks", "Personal tasks","Work Tasks"};

    private int[] tabIcons = {
            R.drawable.urgent,
            R.drawable.personal,
            R.drawable.work

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // call function to initialize views
        init();

        // bind and set tabLayout to viewPager2 and set labels and icons for every tab
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // Inflate custom tab view
            View customTabView = getLayoutInflater().inflate(R.layout.custom_tab_view, null);

            // Find views in the custom tab view
            ImageView tabIcon = customTabView.findViewById(R.id.tab_icon);
            TextView tabText = customTabView.findViewById(R.id.tab_text);

            // Set the icon and text for the tab
            tabIcon.setImageResource(tabIcons[position]);
            tabText.setText(labels[position]);



            // Set the custom tab view for the tab
            tab.setCustomView(customTabView);
        }).attach();

        // set default position to 1 instead of default 0
        viewPager2.setCurrentItem(1, false);


    }

    private void init() {
        // initialize tabLayout
        tabLayout = findViewById(R.id.tab_layout);
        // initialize viewPager2
        viewPager2 = findViewById(R.id.view_pager2);
        // create adapter instance
        adapter = new ViewPagerFragmentAdapter(this);
        // set adapter to viewPager2
        viewPager2.setAdapter(adapter);
    }

    public Context getActivity() {
        return this;
    }

    // create adapter to attach fragments to viewpager2 using FragmentStateAdapter
    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        // return fragments at every position
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new UrgentCardFragment();
                case 1:
                    return new TextCardFragment();
                case 2:
                    return new OfficeCardFragment();


            }
            return new TextCardFragment();

        }

        // return total number of tabs in our case we have 3
        @Override
        public int getItemCount() {
            return labels.length;
        }
    }

}
