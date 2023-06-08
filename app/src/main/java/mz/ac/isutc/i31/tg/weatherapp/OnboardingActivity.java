package mz.ac.isutc.i31.tg.weatherapp;// OnboardingActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnNext;
    private LinearLayout dotsLayout;
    private int[] layouts = {R.layout.onboarding_slide1, R.layout.onboarding_slide2, R.layout.onboarding_slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.button5);
        dotsLayout = findViewById(R.id.layoutDots);


        PagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                // Change button text based on the current page
                if (position == layouts.length - 1) {
                    btnNext.setText("Proximo");
                } else {
                    btnNext.setText("Proximo");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                if (currentPage < layouts.length - 1) {
                    viewPager.setCurrentItem(currentPage + 1);
                } else {

                    startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return layouts.length;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(OnboardingActivity.this);
            ViewGroup layout = (ViewGroup) inflater.inflate(layouts[position], container, false);
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
