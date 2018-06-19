package com.example.mani.studentversion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class LoginSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LoginPreference";
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_STUDENT_ID = "student_id";
    private final String KEY_PASSWORD = "password";

    public static final String KEY_COLLEGE_ID = "college_id";
    public static final String KEY_BRANCH_ID  = "branch_id";
    public static final String KEY_CLASS_ID  = "class_id";


    public static final String KEY_SEMESTER = "semester";
    public static final String KEY_COLLEGE  = "college";
    public static final String KEY_BRANCH   = "branch";
    public static final String KEY_BRANCH_SHORT_NAME   = "branch_short_name";
    public static final String KEY_CLASS    = "class";

    public static final String KEY_NAME     = "name";
    public static final String KEY_DOB      = "dob";
    public static final String KEY_CONTACT  = "contact";
    public static final String KEY_EMAIL    = "email";
    public static final String KEY_GENDER   = "gender";

    // constructor
    public LoginSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

   public void createLoginSession(String student_id, String password,
                                   String college_id, String branch_id,String class_id,
                                   String college, String branch,String branch_short_name, String class_name,String sem,
                                   String name, String dob, String contact, String email, String gender)

    {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_STUDENT_ID, student_id);
        editor.putString(KEY_PASSWORD, password);

        editor.putString(KEY_COLLEGE_ID,college_id);
        editor.putString(KEY_BRANCH_ID,branch_id);
        editor.putString(KEY_CLASS_ID,class_id);

        editor.putString(KEY_COLLEGE,college);
        editor.putString(KEY_BRANCH,branch);
        editor.putString(KEY_BRANCH_SHORT_NAME,branch_short_name);
        editor.putString(KEY_CLASS,class_name);
        editor.putString(KEY_SEMESTER,sem);

        editor.putString(KEY_NAME,name);
        editor.putString(KEY_DOB,dob);
        editor.putString(KEY_CONTACT,contact);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_GENDER,gender);

        editor.commit();
    }

    public void updatePreference(String password, String dob, String contact, String email, String gender){

        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DOB,dob);
        editor.putString(KEY_CONTACT,contact);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_GENDER,gender);

        editor.commit();

    }

    public void checkLogin() {

        if (!this.isLoggedIn()) {

            Intent i = new Intent(context, LoginPage.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }


    public HashMap<String, String> getStudentDetailsFromSharedPreference(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_STUDENT_ID, pref.getString(KEY_STUDENT_ID, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        user.put(KEY_COLLEGE_ID, pref.getString(KEY_COLLEGE_ID, null));
        user.put(KEY_BRANCH_ID, pref.getString(KEY_BRANCH_ID, null));
        user.put(KEY_CLASS_ID, pref.getString(KEY_CLASS_ID, null));

        user.put(KEY_COLLEGE, pref.getString(KEY_COLLEGE, null));
        user.put(KEY_BRANCH, pref.getString(KEY_BRANCH, null));
        user.put(KEY_BRANCH_SHORT_NAME, pref.getString(KEY_BRANCH_SHORT_NAME, null));
        user.put(KEY_CLASS, pref.getString(KEY_CLASS, null));
        user.put(KEY_SEMESTER, pref.getString(KEY_SEMESTER, null));

        user.put(KEY_NAME, pref.getString(KEY_NAME, "You are Awesome"));
        user.put(KEY_DOB, pref.getString(KEY_DOB, null));
        user.put(KEY_CONTACT, pref.getString(KEY_CONTACT, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, "someoneawesom@gmail.com"));

        // gender is saved as int in shared pref but in HashMap it is passed as String
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));

        return user;
    }

    public void logoutStudent(){

        editor.clear();
        editor.commit();


        Intent i = new Intent(context, LoginPage.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        Toast.makeText(context,"Logged Out",Toast.LENGTH_SHORT).show();
        context.startActivity(i);

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
