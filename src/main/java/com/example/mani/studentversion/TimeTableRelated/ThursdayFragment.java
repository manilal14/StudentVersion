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


public class ThursdayFragment extends Fragment {

    EditText t1,t2,t3,t4,t5,t6;
    String sm1,sm2,sm3,sm4,sm5,sm6;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thrusday, container, false);

        sharedPreferences = getContext().getSharedPreferences(TimeTable.MY_PREFERENCES, Context.MODE_PRIVATE);

        t1 = view.findViewById(R.id.eth1);
        t2 = view.findViewById(R.id.eth2);
        t3 = view.findViewById(R.id.eth3);
        t4 = view.findViewById(R.id.eth4);
        t5 = view.findViewById(R.id.eth5);
        t6 = view.findViewById(R.id.eth6);

        setEditTimeTableThursday();


        return view;
    }

    private void setEditTimeTableThursday() {

        t1.setText(sharedPreferences.getString("th1key",null));
        t2.setText(sharedPreferences.getString("th2key",null));
        t3.setText(sharedPreferences.getString("th3key",null));
        t4.setText(sharedPreferences.getString("th4key",null));
        t5.setText(sharedPreferences.getString("th5key",null));
        t6.setText(sharedPreferences.getString("th6key",null));
    }


    /**  This function save the Monday timeTable in shared
     *     prefence on click of save button in TimeTable.
     */
    public void saveThrusdayTimeTable() {

        sm1 = t1.getText().toString().trim();
        sm2 = t2.getText().toString().trim();
        sm3 = t3.getText().toString().trim();
        sm4 = t4.getText().toString().trim();
        sm5 = t5.getText().toString().trim();
        sm6 = t6.getText().toString().trim();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(! (sm1.equals("") && sm2.equals("") && sm3.equals("") &&
                sm4.equals("") && sm5.equals("") && sm6.equals("")) )
        {
            editor.putString("th1key", sm1);
            editor.putString("th2key", sm2);
            editor.putString("th3key", sm3);
            editor.putString("th4key", sm4);
            editor.putString("th5key", sm5);
            editor.putString("th6key", sm6);
            editor.commit();
        }



    }
}
