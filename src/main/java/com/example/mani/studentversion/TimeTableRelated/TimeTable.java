package com.example.mani.studentversion.TimeTableRelated;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mani.studentversion.R;

import java.util.Calendar;

public class TimeTable extends AppCompatActivity {

    public static final String MY_PREFERENCES = "MyPreferences" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        setTimeTable();
        setBackgroundColor();
    }

    @SuppressLint("ResourceAsColor")
    private void setBackgroundColor() {

        LinearLayout llMon, llTue, llWed, llThu, llFri;

        llMon = findViewById(R.id.ll_monday);
        llTue = findViewById(R.id.ll_tuesday);
        llWed = findViewById(R.id.ll_wednesday);
        llThu = findViewById(R.id.ll_thursday);
        llFri = findViewById(R.id.ll_friday);

        Calendar today = Calendar.getInstance();

        // Sunday = 1, Monday = 2 ....
        int day = today.get(Calendar.DAY_OF_WEEK);

        switch (day){

            case 2:
                llMon.setBackgroundColor(Color.parseColor("#F5BCA9"));
                break;

            case 3:
                llTue.setBackgroundColor(Color.parseColor("#F5BCA9"));
                break;

            case 4:
                llWed.setBackgroundColor(Color.parseColor("#F5BCA9"));
                break;

            case 5:
                llThu.setBackgroundColor(Color.parseColor("#F5BCA9"));
                break;

            case 6:
                llFri.setBackgroundColor(Color.parseColor("#F5BCA9"));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_edit_time_table) {
           startActivity(new Intent(TimeTable.this,EditTimeTable.class));
            return true;
        }

        else if(id == R.id.menu_update_time_table){
            Toast.makeText(TimeTable.this,"Feature coming soon",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTimeTable() {

        TextView mon1 = findViewById(R.id.mon1);
        TextView mon2 = findViewById(R.id.mon2);
        TextView mon3 = findViewById(R.id.mon3);
        TextView mon4 = findViewById(R.id.mon4);
        TextView mon5 = findViewById(R.id.mon5);
        TextView mon6 = findViewById(R.id.mon6);

        TextView tue1 = findViewById(R.id.tue1);
        TextView tue2 = findViewById(R.id.tue2);
        TextView tue3 = findViewById(R.id.tue3);
        TextView tue4 = findViewById(R.id.tue4);
        TextView tue5 = findViewById(R.id.tue5);
        TextView tue6 = findViewById(R.id.tue6);

        TextView wed1 = findViewById(R.id.wed1);
        TextView wed2 = findViewById(R.id.wed2);
        TextView wed3 = findViewById(R.id.wed3);
        TextView wed4 = findViewById(R.id.wed4);
        TextView wed5 = findViewById(R.id.wed5);
        TextView wed6 = findViewById(R.id.wed6);

        TextView thr1 = findViewById(R.id.thr1);
        TextView thr2 = findViewById(R.id.thr2);
        TextView thr3 = findViewById(R.id.thr3);
        TextView thr4 = findViewById(R.id.thr4);
        TextView thr5 = findViewById(R.id.thr5);
        TextView thr6 = findViewById(R.id.thr6);

        TextView fri1 = findViewById(R.id.fri1);
        TextView fri2 = findViewById(R.id.fri2);
        TextView fri3 = findViewById(R.id.fri3);
        TextView fri4 = findViewById(R.id.fri4);
        TextView fri5 = findViewById(R.id.fri5);
        TextView fri6 = findViewById(R.id.fri6);

        SharedPreferences sharedPreferences = getSharedPreferences(TimeTable.MY_PREFERENCES,MODE_PRIVATE);

        String m1 = sharedPreferences.getString("mp1key",null);
        String m2 = sharedPreferences.getString("mp2key",null);
        String m3 = sharedPreferences.getString("mp3key",null);
        String m4 = sharedPreferences.getString("mp4key",null);
        String m5 = sharedPreferences.getString("mp5key",null);
        String m6 = sharedPreferences.getString("mp6key",null);

        String t1 = sharedPreferences.getString("t1key",null);
        String t2 = sharedPreferences.getString("t2key",null);
        String t3 = sharedPreferences.getString("t3key",null);
        String t4 = sharedPreferences.getString("t4key",null);
        String t5 = sharedPreferences.getString("t5key",null);
        String t6 = sharedPreferences.getString("t6key",null);

        String w1 = sharedPreferences.getString("w1key",null);
        String w2 = sharedPreferences.getString("w2key",null);
        String w3 = sharedPreferences.getString("w3key",null);
        String w4 = sharedPreferences.getString("w4key",null);
        String w5 = sharedPreferences.getString("w5key",null);
        String w6 = sharedPreferences.getString("w6key",null);

        String th1 = sharedPreferences.getString("th1key",null);
        String th2 = sharedPreferences.getString("th2key",null);
        String th3 = sharedPreferences.getString("th3key",null);
        String th4 = sharedPreferences.getString("th4key",null);
        String th5 = sharedPreferences.getString("th5key",null);
        String th6 = sharedPreferences.getString("th6key",null);

        String f1 = sharedPreferences.getString("f1key",null);
        String f2 = sharedPreferences.getString("f2key",null);
        String f3 = sharedPreferences.getString("f3key",null);
        String f4 = sharedPreferences.getString("f4key",null);
        String f5 = sharedPreferences.getString("f5key",null);
        String f6 = sharedPreferences.getString("f6key",null);


        mon1.setText(m1);
        mon2.setText(m2);
        mon3.setText(m3);
        mon4.setText(m4);
        mon5.setText(m5);
        mon6.setText(m6);

        tue1.setText(t1);
        tue2.setText(t2);
        tue3.setText(t3);
        tue4.setText(t4);
        tue5.setText(t5);
        tue6.setText(t6);

        wed1.setText(w1);
        wed2.setText(w2);
        wed3.setText(w3);
        wed4.setText(w4);
        wed5.setText(w5);
        wed6.setText(w6);

        thr1.setText(th1);
        thr2.setText(th2);
        thr3.setText(th3);
        thr4.setText(th4);
        thr5.setText(th5);
        thr6.setText(th6);

        fri1.setText(f1);
        fri2.setText(f2);
        fri3.setText(f3);
        fri4.setText(f4);
        fri5.setText(f5);
        fri6.setText(f6);

    }

}
