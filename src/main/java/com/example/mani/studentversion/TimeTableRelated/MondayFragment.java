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


public class MondayFragment extends Fragment {

    EditText em1,em2,em3,em4,em5,em6;
    String sm1,sm2,sm3,sm4,sm5,sm6;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_monday, container, false);

        sharedPreferences = getContext().getSharedPreferences(TimeTable.MY_PREFERENCES, Context.MODE_PRIVATE);

        em1 = view.findViewById(R.id.em1);
        em2 = view.findViewById(R.id.em2);
        em3 = view.findViewById(R.id.em3);
        em4 = view.findViewById(R.id.em4);
        em5 = view.findViewById(R.id.em5);
        em6 = view.findViewById(R.id.em6);

        setEditTimeTableMonday();

        return view;
    }

    private void setEditTimeTableMonday(){

        em1.setText(sharedPreferences.getString("mp1key",null));
        em2.setText(sharedPreferences.getString("mp2key",null));
        em3.setText(sharedPreferences.getString("mp3key",null));
        em4.setText(sharedPreferences.getString("mp4key",null));
        em5.setText(sharedPreferences.getString("mp5key",null));
        em6.setText(sharedPreferences.getString("mp6key",null));

    }

    /**  This function save the Monday timeTable in shared
     *     prefence on click of save button in TimeTable.
     */
    public void saveMondayTimeTable() {

        sm1 = em1.getText().toString().trim();
        sm2 = em2.getText().toString().trim();
        sm3 = em3.getText().toString().trim();
        sm4 = em4.getText().toString().trim();
        sm5 = em5.getText().toString().trim();
        sm6 = em6.getText().toString().trim();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(! (sm1.equals("") && sm2.equals("") && sm3.equals("") &&
                sm4.equals("") && sm5.equals("") && sm6.equals("")) )
        {
            editor.putString("mp1key", sm1);
            editor.putString("mp2key", sm2);
            editor.putString("mp3key", sm3);
            editor.putString("mp4key", sm4);
            editor.putString("mp5key", sm5);
            editor.putString("mp6key", sm6);
            editor.commit();
        }



    }



}












