package com.example.mani.studentversion;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mani.studentversion.NewsRelaled.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.accounts.AccountManager.KEY_PASSWORD;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.BASE_URL_ATTENDANCE;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.handleVolleyError;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.maxNoOfTries;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.retrySeconds;
import static com.example.mani.studentversion.LoginSessionManager.KEY_BRANCH;
import static com.example.mani.studentversion.LoginSessionManager.KEY_CLASS;
import static com.example.mani.studentversion.LoginSessionManager.KEY_COLLEGE;
import static com.example.mani.studentversion.LoginSessionManager.KEY_CONTACT;
import static com.example.mani.studentversion.LoginSessionManager.KEY_DOB;
import static com.example.mani.studentversion.LoginSessionManager.KEY_EMAIL;
import static com.example.mani.studentversion.LoginSessionManager.KEY_GENDER;
import static com.example.mani.studentversion.LoginSessionManager.KEY_NAME;
import static com.example.mani.studentversion.LoginSessionManager.KEY_SEMESTER;
import static com.example.mani.studentversion.LoginSessionManager.KEY_STUDENT_ID;

public class EditProfile extends AppCompatActivity {


    LoginSessionManager mLoginSession;
    Calendar mCalendar;

    LinearLayout mMainEditProfileLayout;
    LinearLayout mErrorLinearLayout;
    TextView mErrorTextView;
    Button mRetry;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Error handling
        mMainEditProfileLayout   = findViewById(R.id.main_edit_profile_layout);
        mProgressBar             = findViewById(R.id.progress_bar);
        mErrorLinearLayout       = findViewById(R.id.ll_error_layout);
        mErrorTextView           = findViewById(R.id.tv_error_message);
        mRetry                   = findViewById(R.id.btn_retry);

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainEditProfileLayout.setVisibility(View.VISIBLE);
                mErrorLinearLayout.setVisibility(View.GONE);
            }
        });

        mLoginSession = new LoginSessionManager(getApplicationContext());

        setEditProfile();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_edit_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_save){
            getFromEditProfile();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setEditProfile(){

        EditText et_name, et_college,et_branch,et_sem, et_class;
        EditText et_password, et_dob, et_mobile, et_email;
        RadioButton rb_male, rb_female;
        ImageView profile_pic;

        profile_pic = findViewById(R.id.edit_profile_pic);
        et_name    = findViewById(R.id.edit_profile_name);
        et_college = findViewById(R.id.edit_profile_college);
        et_branch  = findViewById(R.id.edit_profile_branch);
        et_sem     = findViewById(R.id.edit_profile_sem);
        et_class   = findViewById(R.id.edit_profile_class);

        et_password = findViewById(R.id.edit_profile_password);
        et_dob      = findViewById(R.id.edit_profile_dob);
        et_mobile   = findViewById(R.id.edit_profile_mobile);
        et_email    = findViewById(R.id.edit_profile_email);

        rb_male     = findViewById(R.id.radio_button_male);
        rb_female   = findViewById(R.id.radio_button_female);


        HashMap<String, String> student = mLoginSession.getStudentDetailsFromSharedPreference();

        et_name.setText(student.get(KEY_NAME));
        et_college.setText(student.get(KEY_COLLEGE));
        et_branch.setText(student.get(KEY_BRANCH));
        et_sem.setText(student.get(KEY_SEMESTER));
        et_class.setText(student.get(KEY_CLASS));

        et_password.setText(student.get(KEY_PASSWORD));
        et_dob.setText(student.get(KEY_DOB));
        et_mobile.setText(student.get(KEY_CONTACT));
        et_email.setText(student.get(KEY_EMAIL));

        String gender = student.get(KEY_GENDER);

        if(gender.equals("0")){
            profile_pic.setImageResource(R.drawable.ic_male_black);
            rb_male.setChecked(true);

        }
        else{
            profile_pic.setImageResource(R.drawable.ic_female_black);
            rb_female.setChecked(true);
        }
    }

    private void  getFromEditProfile(){

        mProgressBar.setVisibility(View.VISIBLE);
        mMainEditProfileLayout.setVisibility(View.GONE);

        EditText et_password,et_dob, et_contact, et_email;
        RadioGroup rg_gender;

        et_password = findViewById(R.id.edit_profile_password);
        et_dob      = findViewById(R.id.edit_profile_dob);
        et_contact   = findViewById(R.id.edit_profile_mobile);
        et_email    = findViewById(R.id.edit_profile_email);

        rg_gender = findViewById(R.id.radio_group_gender);


        // getting password, dob, contact, email and gender from editText
        String password = et_password.getText().toString().trim();
        String dob      = et_dob.getText().toString().trim();
        String contact  = et_contact.getText().toString().trim();
        String email    = et_email.getText().toString().trim();

        //getting gender
        int id_gender         = rg_gender.getCheckedRadioButtonId();
        RadioButton rb_gender = findViewById(id_gender);
        String gender         = rb_gender.getText().toString();
        // 0 = male , 1 = female
        if(gender.equals("male"))
            gender = "0";
        else
            gender = "1";
        /*Toast.makeText(EditProfile.this,"Gender "+gender
                +" Password "+ password

                        +" DOB "+ dob
                        +" Contact "+ contact
                        +" Email "+ email,
                Toast.LENGTH_SHORT).show();*/

        //Send these data to database(server)
        sendUpdatedDataToDatabase(password,dob,contact,email,gender);

    }
    /*
        It reset password.
        Take password from user and set it to EditText in EditProfile UI
     */
    public void resetPassword(View view) {

        final EditText et_password = (EditText) view;

        //final EditText et_password = findViewById(R.id.edit_profile_password);

        Context context = EditProfile.this;
        final AlertDialog alertDialog;

        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.dialog_reset_password,null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog = new AlertDialog.Builder(context,android.
                    R.style.Theme_DeviceDefault_Light_Dialog_MinWidth).create();
        } else {
            alertDialog = new AlertDialog.Builder(context).create();
        }

        alertDialog.setView(v);

        final EditText et_current_pass,et_new_pass, et_confirm_pass;
        final TextView password_error;
        TextView done,cancel;


        et_current_pass = v.findViewById(R.id.current_password);
        et_new_pass     = v.findViewById(R.id.new_password);
        et_confirm_pass = v.findViewById(R.id.confirm_password);


        password_error = v.findViewById(R.id.tv_password_error);

        done   = v.findViewById(R.id.done);
        cancel = v.findViewById(R.id.cancel);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentPass,newPass,confirmPass;

                currentPass = et_current_pass.getText().toString().trim();
                newPass     = et_new_pass.getText().toString().trim();
                confirmPass = et_confirm_pass.getText().toString().trim();


                if(currentPass.equals("") || newPass.equals("") ||
                        confirmPass.equals("")){
                    password_error.setVisibility(View.VISIBLE);
                    password_error.setText("All field are required");
                    return;
                }

                //if old password is wrong
                if(!currentPass.equals(mLoginSession.getStudentDetailsFromSharedPreference()
                        .get(KEY_PASSWORD))){
                    password_error.setVisibility(View.VISIBLE);
                    password_error.setText("Current password is wrong");
                    et_new_pass.setText("");
                    et_confirm_pass.setText("");

                    return;
                }

                //if new password is shorter than six character
                if(newPass.length()<6){
                    password_error.setVisibility(View.VISIBLE);
                    password_error.setText("New password must be of at least six characters");
                    et_confirm_pass.setText("");
                    return;
                }

                if(!newPass.equals(confirmPass)){
                    password_error.setVisibility(View.VISIBLE);
                    password_error.setText("New password and confirm password do not march");
                    et_confirm_pass.setText("");
                    return;
                }

                password_error.setVisibility(View.GONE);

                //Toast.makeText(EditProfile.this,"new Pass "+newPass,Toast.LENGTH_SHORT).show();

                et_password.setText(newPass);
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

    public void setDOBInEditText(View view) {

        mCalendar =  Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =  new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(EditProfile.this, date, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        String date = sdf.format(mCalendar.getTime());

        EditText et_dob = findViewById(R.id.edit_profile_dob);
        et_dob.setText(date);
    }


    private void sendUpdatedDataToDatabase(final String password, final String dob, final String contact,
                                           final String email, final String gender) {

        final String student_id =  mLoginSession.getStudentDetailsFromSharedPreference().get(KEY_STUDENT_ID);

        String URL_UPDATE_PROFILE = BASE_URL_ATTENDANCE +"updateProfile.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            mProgressBar.setVisibility(View.GONE);

                            JSONArray responseJsonArray = new JSONArray(response);
                            JSONObject jsonObject = responseJsonArray.getJSONObject(0);

                            int responseCode = jsonObject.getInt("response_code");
                            if(responseCode == 1){

                                Toast.makeText(EditProfile.this,"Profile Updated",Toast.LENGTH_SHORT).show();

                                mLoginSession.updatePreference(password,dob,contact,email,gender);
                                finish();
                            }

                            else{

                                mErrorLinearLayout.setVisibility(View.VISIBLE);
                                mErrorTextView.setText("Something went wrong");
                                //Toast.makeText(EditProfile.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                handleVolleyError(error,mProgressBar,mErrorTextView,mErrorLinearLayout);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("student_id",student_id);
                params.put("password",password);
                params.put("dob",dob);
                params.put("contact",contact);
                params.put("email",email);
                params.put("gender",gender);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( (retrySeconds*1000),maxNoOfTries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(EditProfile.this).addToRequestQueue(stringRequest);



    }
}
