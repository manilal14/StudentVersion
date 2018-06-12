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


public class WednesdayFragment extends Fragment {

    EditText w1,w2,w3,w4,w5,w6;
    String sm1,sm2,sm3,sm4,sm5,sm6;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wednesday, container, false);

        sharedPreferences = getContext().getSharedPreferences(TimeTable.MY_PREFERENCES, Context.MODE_PRIVATE);

        w1 = v.findViewById(R.id.ew1);
        w2 = v.findViewById(R.id.ew2);
        w3 = v.findViewById(R.id.ew3);
        w4 = v.findViewById(R.id.ew4);
        w5 = v.findViewById(R.id.ew5);
        w6 = v.findViewById(R.id.ew6);

        setEditTimeTableWednesday();

        return v;
    }

    private void setEditTimeTableWednesday() {

        w1.setText(sharedPreferences.getString("w1key",null));
        w2.setText(sharedPreferences.getString("w2key",null));
        w3.setText(sharedPreferences.getString("w3key",null));
        w4.setText(sharedPreferences.getString("w4key",null));
        w5.setText(sharedPreferences.getString("w5key",null));
        w6.setText(sharedPreferences.getString("w6key",null));


    }

    public void saveWednesdayTimeTable() {

        sm1 = w1.getText().toString().trim();
        sm2 = w2.getText().toString().trim();
        sm3 = w3.getText().toString().trim();
        sm4 = w4.getText().toString().trim();
        sm5 = w5.getText().toString().trim();
        sm6 = w6.getText().toString().trim();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(! (sm1.equals("") && sm2.equals("") && sm3.equals("") &&
                sm4.equals("") && sm5.equals("") && sm6.equals("")) ) {

            editor.putString("w1key", sm1);
            editor.putString("w2key", sm2);
            editor.putString("w3key", sm3);
            editor.putString("w4key", sm4);
            editor.putString("w5key", sm5);
            editor.putString("w6key", sm6);
            editor.commit();
        }


    }
}
