package com.example.mani.studentversion.AttendanceRelated;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.mani.studentversion.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AttendancePredictor extends AppCompatActivity {

    int mPresent, mAbsent, mTotal, mGoal;
    int mTotalExpectedClasses = 150;
    double mPercentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_predictor);

        Bundle bundle = getIntent().getExtras();

        mPresent = bundle.getInt("present");
        mAbsent = bundle.getInt("absent");
        mGoal = bundle.getInt("goal");

        mTotal = mAbsent + mPresent;

        Log.e("Total",String.valueOf(mTotal)+" "+String.valueOf(mPresent)+" "+
                        String.valueOf(mAbsent));

        mPercentage = ((double) mPresent/mTotal) * 100;

        //rounding off percentage upto two decimal places
        mPercentage = Math.round(mPercentage*100)/100.0;
        setLayout();

    }

    @SuppressLint("ResourceAsColor")
    private void setLayout() {

        int classes_left = mTotalExpectedClasses - mTotal;

        TextView tv_total      = findViewById(R.id.total);
        TextView tv_present    = findViewById(R.id.present);
        TextView tv_absent     = findViewById(R.id.absent);
        TextView tv_percentage = findViewById(R.id.percentage);
        TextView tv_goal       = findViewById(R.id.goal);
        TextView tv_classes_left   = findViewById(R.id.total_classes_left);
        TextView tv_need_to_attend = findViewById(R.id.need_to_attend);
        TextView tv_message = findViewById(R.id.message);



        tv_total.setText(String.valueOf(mTotal));
        tv_absent.setText(String.valueOf(mAbsent));
        tv_present.setText(String.valueOf(mPresent));
        tv_percentage.setText(String.valueOf(mPercentage)+" %");
        tv_goal.setText("Goal set : " +mGoal + " %");
        tv_classes_left.setText(String.valueOf(classes_left));

        double total_class_to_be_attended = Math.round((double) mTotalExpectedClasses * mGoal) / 100.0;

        int need_to_attend_more = (int) Math.ceil(total_class_to_be_attended) - mPresent;


        if(need_to_attend_more > classes_left || need_to_attend_more < 0) {
            tv_message.setText("Tumse na ho payega");
            tv_message.setTextColor(getResources().getColor(R.color.red));
        }
        else{

            String message = "You need to attend " + need_to_attend_more + " more classes out of " + classes_left +
                    " classes " + " to reach your "+ mGoal +" % goal";
            tv_message.setText(message);
            setGraph(need_to_attend_more,classes_left);

        }
        tv_need_to_attend.setText(String.valueOf(need_to_attend_more));
    }

    private void setGraph(int need_to_attend_more, int classes_left) {

        PieChart pieChart = findViewById(R.id.pieChart_predict);

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setExtraOffsets(5,0,5,0);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        pieChart.setHoleRadius(43);
        //To disable the legend
        pieChart.getLegend().setEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        double percentage_to_attend = ((double) need_to_attend_more/classes_left) * 100;
        percentage_to_attend = Math.round(percentage_to_attend*100)/100.0;

        pieChart.setCenterText(String.valueOf(percentage_to_attend)+" %");
        pieChart.setCenterTextSize(15f);

        yValues.add(new PieEntry((classes_left - need_to_attend_more),"Can skip"));
        yValues.add(new PieEntry(need_to_attend_more,"Must attend"));

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(7f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);
    }
}
