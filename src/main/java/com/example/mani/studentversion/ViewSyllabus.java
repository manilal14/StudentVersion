package com.example.mani.studentversion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.mani.studentversion.CommonVariablesAndFunctions.BASE_URL_SYLLABUS;
import static com.example.mani.studentversion.LoginSessionManager.KEY_BRANCH_SHORT_NAME;
import static com.example.mani.studentversion.LoginSessionManager.KEY_SEMESTER;


public class ViewSyllabus extends AppCompatActivity {

    PDFView mPdfView;
    String SYLLABUS_URL;
    LinearLayout progressBar;
    InputStream mInputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_syllabus);

        init();
        new RetrievePDFSteam().execute(SYLLABUS_URL);
    }

    private void init() {

        String branch_short_name = new LoginSessionManager(ViewSyllabus.this)
                .getStudentDetailsFromSharedPreference()
                .get(KEY_BRANCH_SHORT_NAME);

        int semester_id = Integer.parseInt(new LoginSessionManager(ViewSyllabus.this)
                .getStudentDetailsFromSharedPreference()
                .get(KEY_SEMESTER));

        //Toast.makeText(ViewSyllabus.this,semester_id+"",Toast.LENGTH_SHORT).show();

        SYLLABUS_URL = BASE_URL_SYLLABUS + branch_short_name + "/" + semester_id+".pdf";

        mPdfView    = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.progress_bar);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_syllabus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.menu_update_syllabus:
                new RetrievePDFSteam().execute(SYLLABUS_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class RetrievePDFSteam extends AsyncTask<String, Void,InputStream> {


        @Override
        protected void onPreExecute() {
            //Toast.makeText(ViewSyllabus.this,"PreExecute",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected InputStream doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode() == 200){
                    mInputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mInputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            //Toast.makeText(ViewSyllabus.this,"PostExecute",Toast.LENGTH_SHORT).show();

            if(inputStream!=null)
                mPdfView.fromStream(inputStream).load();
            else
                Toast.makeText(ViewSyllabus.this,"Syllabus not available", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);


        }
    }

}
