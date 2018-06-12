package com.example.mani.studentversion.TimeTableRelated;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mani.studentversion.R;


public class FridayFragment extends Fragment {

    EditText er1,er2,er3,er4,er5,er6;
    String sr1,sr2,sr3,sr4,sr5,sr6;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_friday, container, false);

        sharedPreferences = getContext().getSharedPreferences(TimeTable.MY_PREFERENCES, Context.MODE_PRIVATE);

        er1 = view.findViewById(R.id.ef1);
        er2 = view.findViewById(R.id.ef2);
        er3 = view.findViewById(R.id.ef3);
        er4 = view.findViewById(R.id.ef4);
        er5 = view.findViewById(R.id.ef5);
        er6 = view.findViewById(R.id.ef6);

        setEditTimeTableFriday();



        return view;
    }

    private void setEditTimeTableFriday() {

        er1.setText(sharedPreferences.getString("f1key",null));
        er2.setText(sharedPreferences.getString("f2key",null));
        er3.setText(sharedPreferences.getString("f3key",null));
        er4.setText(sharedPreferences.getString("f4key",null));
        er5.setText(sharedPreferences.getString("f5key",null));
        er6.setText(sharedPreferences.getString("f6key",null));
    }

    /**  This function save the Monday timeTable in shared
     *     prefence on click of save button in TimeTable.
     */
    public void saveFridayTimeTable() {

        sr1 = er1.getText().toString().trim();
        sr2 = er2.getText().toString().trim();
        sr3 = er3.getText().toString().trim();
        sr4 = er4.getText().toString().trim();
        sr5 = er5.getText().toString().trim();
        sr6 = er6.getText().toString().trim();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(! (sr1.equals("") && sr2.equals("") && sr3.equals("") &&
                sr4.equals("") && sr5.equals("") && sr6.equals("")) )
        {
            editor.putString("f1key", sr1);
            editor.putString("f2key", sr2);
            editor.putString("f3key", sr3);
            editor.putString("f4key", sr4);
            editor.putString("f5key", sr5);
            editor.putString("f6key", sr6);
            editor.commit();
        }



    }
}
