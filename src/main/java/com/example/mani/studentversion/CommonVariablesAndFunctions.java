package com.example.mani.studentversion;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class CommonVariablesAndFunctions {

    public static final int retrySeconds = 5;
    public static final int maxNoOfTries = 1;
    public static final String BASE_URL = "http://192.168.43.153/sudoApp/";
    public static final String BASE_URL_ATTENDANCE = "http://192.168.43.153/sudoApp_attendance/";


    public  static final String BASE_URL_SYLLABUS = BASE_URL + "syllabus/2015/";

    public static boolean postingNewFeed = false;
    public static boolean skipedLogin = false;


    public static void handleVolleyError(VolleyError error, SwipeRefreshLayout swipeRefreshLayout,
                                         TextView errorTextView, LinearLayout linearLayout){

        swipeRefreshLayout.setRefreshing(false);
        linearLayout.setVisibility(View.VISIBLE);

        if(error instanceof TimeoutError){
            errorTextView.setText(R.string.connection_error);
        }
        else if (error instanceof NoConnectionError){
            errorTextView.setText(R.string.no_connection);
        }
        else if (error instanceof AuthFailureError){
            errorTextView.setText(R.string.auth_failure_error);
        }
        else if (error instanceof ServerError){
            errorTextView.setText(R.string.server_error);
        }
        else if (error instanceof NetworkError){
            errorTextView.setText(R.string.network_error);
        }
        else if(error instanceof ParseError){
            errorTextView.setText(R.string.parse_error);
        }
        else {
            errorTextView.setText(R.string.volley_error);
        }
    }


    public static void handleVolleyError(VolleyError error, ProgressBar progressBar,
                                         TextView errorTextView, LinearLayout linearLayout){

        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        if(error instanceof TimeoutError){
            errorTextView.setText(R.string.connection_error);
        }
        else if (error instanceof NoConnectionError){
            errorTextView.setText(R.string.no_connection);
        }
        else if (error instanceof AuthFailureError){
            errorTextView.setText(R.string.auth_failure_error);
        }
        else if (error instanceof ServerError){
            errorTextView.setText(R.string.server_error);
        }
        else if (error instanceof NetworkError){
            errorTextView.setText(R.string.network_error);
        }
        else if(error instanceof ParseError){
            errorTextView.setText(R.string.parse_error);
        }
        else {
            errorTextView.setText(R.string.volley_error);
        }

    }


}
