package com.example.mani.studentversion.AttendanceRelated;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mani.studentversion.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AttendanceReportAdapter extends RecyclerView.Adapter
        <AttendanceReportAdapter.AttendanceViewHolder> {

    Context mctx;
    ArrayList<AttendanceReport> mAttendanceList;

    public AttendanceReportAdapter(Context mctx, ArrayList<AttendanceReport> mAttendanceList) {
        this.mctx = mctx;
        this.mAttendanceList = mAttendanceList;
    }

    @NonNull
    @Override
    public AttendanceReportAdapter.AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View v = inflater.inflate(R.layout.check_attendance_single_layout,parent,false);
        return new AttendanceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceReportAdapter.AttendanceViewHolder holder, int position) {

        AttendanceReport attendance = mAttendanceList.get(position);

        holder.tv_subject_name.setText(attendance.getSubName());

        holder.pieChart.setUsePercentValues(false);
        holder.pieChart.getDescription().setEnabled(false);
        holder.pieChart.setDragDecelerationFrictionCoef(0.99f);
        holder.pieChart.setExtraOffsets(5,0,5,0);
        holder.pieChart.setDrawHoleEnabled(true);
        holder.pieChart.setHoleColor(Color.WHITE);
        holder.pieChart.setTransparentCircleRadius(60f);
        holder.pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);

        holder.pieChart.setHoleRadius(45);
        holder.pieChart.getLegend().setEnabled(false);

        int presentHrs = attendance.getPresentHrs();
        int absentHrs = attendance.getAbsentHrs();
        int totalHrs = attendance.getTotalHrs();

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(presentHrs,"Present"));
        yValues.add(new PieEntry(absentHrs,"Absent"));

        double percentage = ((double) presentHrs/totalHrs) * 100;
        double roundoff = Math.round(percentage*100)/100.0;

        holder.pieChart.setCenterText(String.valueOf(roundoff)+" %");
        holder.pieChart.setCenterTextSize(16f);
        if(percentage < 75.00)
            holder.pieChart.setCenterTextColor(Color.RED);
        else
            holder.pieChart.setCenterTextColor(Color.CYAN);



        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.YELLOW);
        holder.pieChart.setData(data);
    }

    @Override
    public int getItemCount() {
        return mAttendanceList.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder{

        PieChart pieChart;
        TextView tv_subject_name;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            pieChart = itemView.findViewById(R.id.pieChart);
            tv_subject_name = itemView.findViewById(R.id.tv_sub_name);
        }
    }
}
