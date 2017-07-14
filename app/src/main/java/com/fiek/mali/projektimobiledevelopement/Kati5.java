package com.fiek.mali.projektimobiledevelopement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Kati5 extends AppCompatActivity {

    private Databaza objDB;
    private static final String lecturesDBURL = "http://sample-env-2.hvzudatm2n.us-west-2.elasticbeanstalk.com/my-service.php";
    private static final String commentDBURL = "http://sample-env-2.hvzudatm2n.us-west-2.elasticbeanstalk.com/comments.php?t=0";
    String username;

    PhotoView imgvKati5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kati5);
        imgvKati5 = (PhotoView) findViewById(R.id.imgvKati5);

        Intent myintent = getIntent();
        username = myintent.getStringExtra("username");

        objDB = new Databaza(this);

        if (isNetworkAvailable())
        {
            new Kati5.RetrieveSchedule().execute();
            new Kati5.RetrieveComments().execute();
        }
        else {
            Toast.makeText(this,"Schedule might be not updated! Please connect to the internet to update.",Toast.LENGTH_LONG).show();
            colorAvailability("501");
            colorAvailability("502");
            colorAvailability("507");
            colorAvailability("511");
            colorAvailability("526");
            colorAvailability("521");
            colorAvailability("516");
            colorAvailability("515");
            colorAvailability("514");
            colorAvailability("513");
        }

        imgvKati5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.RED, touch_color, tolerance)) {
                            //Vendoset intenti per me kalu ne klase
                            Intent int501 = new Intent(getApplicationContext(), Klasat.class);
                            int501.putExtra("nrSalla","501");
                            int501.putExtra("username",username);
                            startActivity(int501);
                        }
                        if (closeMatch(Color.YELLOW, touch_color, tolerance)) {
                            Intent int502 = new Intent(getApplicationContext(), Klasat.class);
                            int502.putExtra("nrSalla","502");
                            int502.putExtra("username",username);
                            startActivity(int502);
                        }
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Intent int507 = new Intent(getApplicationContext(), Klasat.class);
                            int507.putExtra("nrSalla","507");
                            int507.putExtra("username",username);
                            startActivity(int507);
                        }
                        if (closeMatch(Color.GREEN, touch_color, tolerance)) {
                            Intent int511 = new Intent(getApplicationContext(), Klasat.class);
                            int511.putExtra("nrSalla","511");
                            int511.putExtra("username",username);
                            startActivity(int511);
                        }
                        if (closeMatch(Color.rgb(127, 0, 0), touch_color, tolerance)) {
                            Intent int526 = new Intent(getApplicationContext(), Klasat.class);
                            int526.putExtra("nrSalla","526");
                            int526.putExtra("username",username);
                            startActivity(int526);
                        }
                        if (closeMatch(Color.rgb(56, 116, 65), touch_color, tolerance)) {
                            Intent int521 = new Intent(getApplicationContext(), Klasat.class);
                            int521.putExtra("nrSalla","521");
                            int521.putExtra("username",username);
                            startActivity(int521);
                        }
                        if (closeMatch(Color.WHITE, touch_color, tolerance)) {
                            Intent int516 = new Intent(getApplicationContext(), Klasat.class);
                            int516.putExtra("nrSalla","516");
                            int516.putExtra("username",username);
                            startActivity(int516);
                        }
                        if (closeMatch(Color.rgb(255, 0, 255), touch_color, tolerance)) {
                            Intent int515 = new Intent(getApplicationContext(), Klasat.class);
                            int515.putExtra("nrSalla","515");
                            int515.putExtra("username",username);
                            startActivity(int515);
                        }
                        if (closeMatch(Color.rgb(0, 255, 255), touch_color, tolerance)) {
                            Intent int514 = new Intent(getApplicationContext(), Klasat.class);
                            int514.putExtra("nrSalla","514");
                            int514.putExtra("username",username);
                            startActivity(int514);
                        }
                        if (closeMatch(Color.rgb(127, 0, 127), touch_color, tolerance)) {
                            Intent int513 = new Intent(getApplicationContext(), Klasat.class);
                            int513.putExtra("nrSalla","513");
                            int513.putExtra("username",username);
                            startActivity(int513);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private int getHotspotColor(int x, int y) {
        PhotoView img = (PhotoView) findViewById(R.id.imgvArea_kati5_click);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public boolean closeMatch(int color1, int color2, int tolerance) {
        return Math.abs(Color.blue(color1) - Color.blue(color2)) <= tolerance
                && Math.abs (Color.green (color1) - Color.green (color2)) <= tolerance
                && Math.abs (Color.red (color1) - Color.red (color2)) <= tolerance;
    }

    private void colorAvailability(String classnumber) {

        Cursor objCursor = objDB.getTodayLecturesTimes(classnumber);
        ArrayList<String> startTime = new ArrayList<>();
        ArrayList<String> endTime = new ArrayList<>();

        int nrRows = objCursor.getCount();

        if(nrRows >0 ) {
            for (objCursor.moveToFirst(); !objCursor.isAfterLast(); objCursor.moveToNext()) {
                // TO DO code here
                startTime.add(objCursor.getString(0));
                endTime.add(objCursor.getString(1));
            }
        }

        objCursor.close();
        ImageView imageView;
        if(nrRows < 1) {
            String nrClass = "imgvkati5_" + classnumber +"_green";
            int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
            imageView = (ImageView) findViewById(resID);
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            int numberOfRows = startTime.size();
            for (int i = 0; i < numberOfRows; i++)
            {
                try {
                    LocalTime now = LocalTime.now();

                    String strStarttime = startTime.get(i);
                    String strEndtime = endTime.get(i);

                    LocalTime ltStarttime = new LocalTime(strStarttime);
                    LocalTime ltEndtime = new LocalTime(strEndtime);

                    if (now.isAfter(ltStarttime) && now.isBefore(ltEndtime)) {
                        String nrClass = "imgvkati5_" + classnumber + "_red";
                        int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                        imageView = (ImageView) findViewById(resID);
                        imageView.setVisibility(View.VISIBLE);
                    }
                    else {
                        String nrClass = "imgvkati5_" + classnumber + "_green";
                        int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                        imageView = (ImageView) findViewById(resID);
                        imageView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public class RetrieveComments extends AsyncTask<Void,Void,JSONArray> {

        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            StringBuilder urlString = new StringBuilder();
            urlString.append(commentDBURL);

            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONArray objJSON = null;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objInStream = objURLConnection.getInputStream();
                BufferedReader objBReader = new BufferedReader(new InputStreamReader(objInStream));
                String line;
                String response = "";
                while ((line = objBReader.readLine()) != null) {
                    response += line;
                }
                objJSON = (JSONArray) new JSONTokener(response).nextValue();
            } catch (Exception e) {
                this.mException = e;
            } finally {
                if (objInStream != null) {
                    try {
                        objInStream.close(); // this will close the bReader as well
                    } catch (IOException ignored) {
                    }
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return objJSON;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            if (this.mException != null) {
                Log.e("JSON Exception", this.mException.toString());
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObjectLecture = result.getJSONObject(i);
                    int commentID = jsonObjectLecture.getInt("id");
                    String commentClassroom = jsonObjectLecture.getString("classroom");
                    String commentContent = jsonObjectLecture.getString("commentcontent");
                    String reg_date = jsonObjectLecture.getString("reg_date");
                    objDB.insertCommentOrIgnore(commentID, commentClassroom, commentContent,reg_date);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class RetrieveSchedule extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog objProgressDialog;
        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
            objProgressDialog = ProgressDialog.show(Kati5.this,
                    "Loading schedule...","Please wait while the schedule is downloading", true);
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(lecturesDBURL);

            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONArray objJSON = null;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objInStream = objURLConnection.getInputStream();
                BufferedReader objBReader = new BufferedReader(new InputStreamReader(objInStream));
                String line;
                String response = "";
                while ((line = objBReader.readLine()) != null) {
                    response += line;
                }
                objJSON = (JSONArray) new JSONTokener(response).nextValue();
            } catch (Exception e) {
                this.mException = e;
            } finally {
                if (objInStream != null) {
                    try {
                        objInStream.close(); // this will close the bReader as well
                    } catch (IOException ignored) {
                    }
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return objJSON;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            objProgressDialog.dismiss();

            if (this.mException != null) {
                Log.e("JSON Exception", this.mException.toString());
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObjectLecture = result.getJSONObject(i);
                    int lectureID = jsonObjectLecture.getInt("id");
                    String lectureDay = jsonObjectLecture.getString("dita");
                    String lectureClassNumber = jsonObjectLecture.getString("nrKlasa");
                    String lectureStartTime = jsonObjectLecture.getString("starttime");
                    String lectureEndTime = jsonObjectLecture.getString("endtime");
                    String lectureGrupi = jsonObjectLecture.getString("grupi");
                    String lectureType = jsonObjectLecture.getString("ligjerata_ushtrime");
                    String lectureProfesor = jsonObjectLecture.getString("prof");
                    String lectureSubject = jsonObjectLecture.getString("lenda");

                    objDB.insertLectureOrIgnore(lectureID, lectureDay, lectureClassNumber, lectureStartTime, lectureEndTime, lectureGrupi, lectureType,lectureProfesor,lectureSubject );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            colorAvailability("501");
            colorAvailability("502");
            colorAvailability("507");
            colorAvailability("511");
            colorAvailability("526");
            colorAvailability("521");
            colorAvailability("516");
            colorAvailability("515");
            colorAvailability("514");
            colorAvailability("513");
        }
    }
}
