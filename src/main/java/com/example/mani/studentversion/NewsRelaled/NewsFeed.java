package com.example.mani.studentversion.NewsRelaled;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mani.studentversion.AttendanceRelated.CheckAttendance;
import com.example.mani.studentversion.EditProfile;
import com.example.mani.studentversion.LoginPage;
import com.example.mani.studentversion.LoginSessionManager;
import com.example.mani.studentversion.R;
import com.example.mani.studentversion.TimeTableRelated.TimeTable;
import com.example.mani.studentversion.ViewSyllabus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.mani.studentversion.CommonVariablesAndFunctions.BASE_URL;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.handleVolleyError;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.maxNoOfTries;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.postingNewFeed;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.retrySeconds;
import static com.example.mani.studentversion.CommonVariablesAndFunctions.skipedLogin;

public class NewsFeed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Feeds> mFeedsList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FeedsAdapter mFeedAdapter;

    LinearLayout mErrorLinearLayout;
    TextView mErrorTextView;
    Button mRetry;

    NavigationView mNavigationView;
    Menu mMenu;

    LoginSessionManager mLoginSession;


    private static final String FETCHING_URL = BASE_URL + "fetch_from_database_to_app.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        mLoginSession = new LoginSessionManager(getApplicationContext());

        if(skipedLogin == false) {

            if (!mLoginSession.isLoggedIn()) {
                mLoginSession.checkLogin();
                finish();
            }
        }

        // If skipedLoginCheck == true then execution will start from here
        if(skipedLogin == true)
            Toast.makeText(NewsFeed.this,"Login is skipped",Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Error handling Views
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mErrorLinearLayout  = findViewById(R.id.ll_error_layout);
        mErrorTextView      = findViewById(R.id.tv_error_message);
        mRetry              = findViewById(R.id.btn_retry);

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFeedsFromDatabase();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Setting navigation header with name, email an profilePic
        setNavigationHeader();

        mFeedsList =  new ArrayList<>();

        loadFeedsFromDatabase();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeedsFromDatabase();
            }
        });

        //setMenuTitle();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            //android.os.Process.killProcess(android.os.Process.myPid());
                            skipedLogin = false;
                            finish();
                        }
                    }).create().show();
        }
    }

    @Override
    protected void onResume() {
        setNavigationHeader();
        if(postingNewFeed) {
            loadFeedsFromDatabase();
            postingNewFeed = false;
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_feed, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_refresh) {
            loadFeedsFromDatabase();
        }
        else if (id == R.id.menu_login_logout) {

            if(mLoginSession.isLoggedIn())
                mLoginSession.logoutStudent();
            else
                startActivity(new Intent(NewsFeed.this, LoginPage.class));

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem login_logout = menu.findItem(R.id.menu_login_logout);

        if(mLoginSession.isLoggedIn())
            login_logout.setTitle("Logout");
        else
            login_logout.setTitle("Login");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_timetable) {
            startActivity(new Intent(NewsFeed.this,TimeTable.class));

        } else if (id == R.id.nav_attendence) {

            if (!mLoginSession.isLoggedIn()) {
                Toast.makeText(NewsFeed.this,"You must Login first",Toast.LENGTH_SHORT).show();
                return true;
            }

            startActivity(new Intent(NewsFeed.this, CheckAttendance.class));

        } else if (id == R.id.nav_chatroom) {

            if (!mLoginSession.isLoggedIn()) {
                Toast.makeText(NewsFeed.this,"You must Login first",Toast.LENGTH_SHORT).show();
                return true;
            }


        } else if (id == R.id.nav_certificate) {

            if (!mLoginSession.isLoggedIn()) {
                Toast.makeText(NewsFeed.this,"You must Login first",Toast.LENGTH_SHORT).show();
                return true;
            }

        } else if (id == R.id.nav_syllabus) {
            if (!mLoginSession.isLoggedIn()) {
                Toast.makeText(NewsFeed.this,"You must Login first",Toast.LENGTH_SHORT).show();
                return true;
            }
            startActivity(new Intent(NewsFeed.this,ViewSyllabus.class));

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationHeader(){

        View header;
        TextView headerName,headerEmail;
        ImageView headerProfilePic, headerEditProfile;

        header = mNavigationView.getHeaderView(0);

        headerName         = header.findViewById(R.id.header_name);
        headerEmail        = header.findViewById(R.id.header_email);
        headerProfilePic   = header.findViewById(R.id.header_image);
        headerEditProfile  = header.findViewById(R.id.header_edit_profile);

        headerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginSession.isLoggedIn()) {
                    // First close the drawer then start activity
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(NewsFeed.this, EditProfile.class));
                }
                else
                    Toast.makeText(NewsFeed.this,"You must Login to edit your profile",
                            Toast.LENGTH_SHORT).show();
            }
        });

        HashMap<String, String> student = mLoginSession.getStudentDetailsFromSharedPreference();

        String name       = student.get(LoginSessionManager.KEY_NAME);
        String email      = student.get(LoginSessionManager.KEY_EMAIL);
        String gender     = student.get(LoginSessionManager.KEY_GENDER);

        headerName.setText(name);
        headerEmail.setText(email);

        if(skipedLogin == false) {
            if (gender.equals("0"))
                headerProfilePic.setImageResource(R.drawable.ic_male);
            else
                headerProfilePic.setImageResource(R.drawable.ic_female);
        }
    }

    private void loadFeedsFromDatabase()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        mErrorLinearLayout.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCHING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // First clear the adaptar if something is there
                        // useful on refreshing
                        if(mFeedAdapter != null) {
                            mFeedAdapter.clear();
                            mFeedAdapter.addAll(mFeedsList);
                        }

                        try {

                            JSONArray products = new JSONArray(response);

                            for(int i=0;i<products.length();i++) {

                                JSONObject productObject = products.getJSONObject(i);

                                int id           = productObject.getInt("id");
                                String title     = productObject.getString("title");
                                String description = productObject.getString("description");
                                int imageInt     = productObject.getInt("image_path");
                                String image = Integer.toString(imageInt) + ".jpeg";

                                image = BASE_URL + "uploaded_image/" + image;

                                Feeds feeds = new Feeds(id,title,description,image);
                                mFeedsList.add(feeds);
                            }

                            mSwipeRefreshLayout.setRefreshing(false);

                            RecyclerView recyclerView = findViewById(R.id.news_feed_recycylerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(NewsFeed.this));

                            mFeedAdapter = new FeedsAdapter(NewsFeed.this,mFeedsList);
                            recyclerView.setAdapter(mFeedAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error,mSwipeRefreshLayout,mErrorTextView,mErrorLinearLayout);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( (retrySeconds * 1000),maxNoOfTries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(stringRequest);
    }


}


