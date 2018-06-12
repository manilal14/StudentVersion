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


public class TuesdayFragment extends Fragment {
    EditText t1,t2,t3,t4,t5,t6;
    String sm1,sm2,sm3,sm4,sm5,sm6;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tuesday, container, false);

        sharedPreferences = getContext().getSharedPreferences(TimeTable.MY_PREFERENCES, Context.MODE_PRIVATE);

        t1 = view.findViewById(R.id.et1);
        t2 = view.findViewById(R.id.et2);
        t3 = view.findViewById(R.id.et3);
        t4 = view.findViewById(R.id.et4);
        t5 = view.findViewById(R.id.et5);
        t6 = view.findViewById(R.id.et6);

        setEditTimeTableTuesday();

        return view;
    }

    private void setEditTimeTableTuesday() {

        t1.setText(sharedPreferences.getString("t1key",null));
        t2.setText(sharedPreferences.getString("t2key",null));
        t3.setText(sharedPreferences.getString("t3key",null));
        t4.setText(sharedPreferences.getString("t4key",null));
        t5.setText(sharedPreferences.getString("t6key",null));
        t6.setText(sharedPreferences.getString("t6key",null));
    }


    public void saveTuesdayTimeTable() {

        sm1 = t1.getText().toString().trim();
        sm2 = t2.getText().toString().trim();
        sm3 = t3.getText().toString().trim();
        sm4 = t4.getText().toString().trim();
        sm5 = t5.getText().toString().trim();
        sm6 = t6.getText().toString().trim();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(! (sm1.equals("") && sm2.equals("") && sm3.equals("") &&
                sm4.equals("") && sm5.equals("") && sm6.equals("")) ) {

            editor.putString("t1key", sm1);
            editor.putString("t2key", sm2);
            editor.putString("t3key", sm3);
            editor.putString("t4key", sm4);
            editor.putString("t5key", sm5);
            editor.putString("t6key", sm6);
            editor.commit();
        }


    }


}
