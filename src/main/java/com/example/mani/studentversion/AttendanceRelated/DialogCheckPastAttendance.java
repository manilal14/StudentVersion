package com.example.mani.studentversion.AttendanceRelated;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mani.studentversion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DialogCheckPastAttendance {

    Context mCtx;
    List <Subject> subjectList = new ArrayList<>();

    EditText mDialogDate;
    Spinner mDialogPeriod;
    Spinner mDialogClass;

    public DialogCheckPastAttendance(Context mCtx, List <Subject> subjectList){

        this.mCtx = mCtx;
        this.subjectList = subjectList;
    }

    public void setDialogBox() {


        final AlertDialog alertDialog;

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View v = inflater.inflate(R.layout.dialog_check_past_attendance,null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(mCtx,android.
                    R.style.Theme_DeviceDefault_Light_Dialog_MinWidth).create();
        } else {
            alertDialog = new AlertDialog.Builder(mCtx).create();
        }

        alertDialog.setView(v);

        mDialogDate   = v.findViewById(R.id.dialog_date);
        mDialogPeriod = v.findViewById(R.id.dialog_period);
        mDialogClass  = v.findViewById(R.id.dialog_class);

        TextView cancel = v.findViewById(R.id.dialog_cancel);
        TextView done   = v.findViewById(R.id.dialog_done);

        // Select Date
        mDialogDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    launchCalender();
            }
        });

        // period and class name is set as spinner
        Integer[] periods_spinner     = {1,2,3,4,5,6};
        List<String> classes_for_spinner = new ArrayList<>();

        for(int i=0;i<subjectList.size();i++){

            String sem        = subjectList.get(i).getSemester();
            String class_name = subjectList.get(i).getClassName();
            classes_for_spinner.add(sem +"   "+ class_name);

        }

        ArrayAdapter<Integer> adapter_for_period = new ArrayAdapter<>(mCtx,
                android.R.layout.simple_list_item_1,periods_spinner);
        mDialogPeriod.setAdapter(adapter_for_period);

        ArrayAdapter<String> adapter_for_class = new ArrayAdapter<>(mCtx,
                android.R.layout.simple_list_item_1,classes_for_spinner);

        mDialogClass.setAdapter(adapter_for_class);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date     = mDialogDate.getText().toString().trim();
                Integer period   = (Integer) mDialogPeriod.getSelectedItem();

                // get index from spinner and from subjectList gets class id.
                int index = mDialogClass.getSelectedItemPosition();
                int class_id = subjectList.get(index).getClass_id();

                if(date.equals("")){
                    Toast.makeText(mCtx,"Enter date",Toast.LENGTH_SHORT).show();
                    return;
                }

                /*Toast.makeText(mCtx,""+date+" "+period+"  "+class_id,
                        Toast.LENGTH_SHORT).show();*/

                Intent i = new Intent(mCtx,CheckPastAttendance.class);

                Bundle bundle = new Bundle();

                bundle.putString("date",date);
                bundle.putInt("period",period);
                bundle.putInt("class_id",class_id);

                i.putExtras(bundle);

                mCtx.startActivity(i);
                alertDialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    // On clicking select date in dialog
    public void launchCalender() {

        final Calendar calendar =  Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =  new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendar);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(mCtx, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    private void updateLabel(Calendar calendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        String date = sdf.format(calendar.getTime());
        mDialogDate.setText(date);
    }
}
