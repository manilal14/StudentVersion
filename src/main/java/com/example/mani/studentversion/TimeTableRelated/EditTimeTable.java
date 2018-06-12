package com.example.mani.studentversion.TimeTableRelated;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mani.studentversion.R;

import java.util.ArrayList;

public class EditTimeTable extends AppCompatActivity {

    ArrayList<android.support.v4.app.Fragment> fr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);

        // To remove the shadow between appbar and tabbar
        getSupportActionBar().setElevation(0);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        fr = new ArrayList<>();
        fr.add(new MondayFragment());
        fr.add(new TuesdayFragment());
        fr.add(new WednesdayFragment());
        fr.add(new ThursdayFragment());
        fr.add(new FridayFragment());

        EditTimeTableFragmentPagerAdapter adapter = new EditTimeTableFragmentPagerAdapter(getSupportFragmentManager(),fr);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_time_table,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_time_table) {

            MondayFragment m = (MondayFragment) fr.get(0);
            m.saveMondayTimeTable();

            TuesdayFragment t = (TuesdayFragment) fr.get(1);
            t.saveTuesdayTimeTable();

            WednesdayFragment w = (WednesdayFragment) fr.get(2);
            w.saveWednesdayTimeTable();

            ThursdayFragment th = (ThursdayFragment) fr.get(3);
            th.saveThrusdayTimeTable();

            FridayFragment f = (FridayFragment) fr.get(4);
            f.saveFridayTimeTable();

            Intent i = new Intent(EditTimeTable.this, TimeTable.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
