package com.example.user.attendance_tracker;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private  int tabIcons[]={R.drawable.student_login,
            R.drawable.teacher_login};
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      viewPager = findViewById(R.id.container);
      setupViewPager(viewPager);
      tabLayout = findViewById(R.id.tabLayout);
      tabLayout.setupWithViewPager(viewPager);
      setUpTabIcons();

    }

    private void setUpTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter= new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFrag(new StudentLogin(),"Student");
        adapter.addFrag(new TeacherLogin(),"Teacher");
        viewPager.setAdapter(adapter);

    }
    class ViewPageAdapter extends FragmentPagerAdapter{

        private  final List<Fragment> mFramentList = new ArrayList<>();
        private  final List<String> mFragmentTitle = new ArrayList<>();
        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFramentList.get(position);
        }
        public void addFrag(Fragment fragment,String title)
        {
            mFramentList.add(fragment);
            mFragmentTitle.add(title);

        }

        @Override
        public int getCount() {
            return mFramentList.size();
        }

    }
}
