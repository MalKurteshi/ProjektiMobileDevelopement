package com.fiek.mali.projektimobiledevelopement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Klasat extends AppCompatActivity {

    // Local class variables
    private ListView listComments;
    private EditText content;
    private Databaza objDB;

    private static final String commentDBURL2 = "http://sample-env-2.hvzudatm2n.us-west-2.elasticbeanstalk.com/comments.php?t=0";
    private static final String commentDBURL = "http://sample-env-2.hvzudatm2n.us-west-2.elasticbeanstalk.com/comments.php?t=1&classroom=%22%22&commentcontent=%22%22&user=";

    PhotoView imgKlasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasat);
        imgKlasa = (PhotoView) findViewById(R.id.imgvClassPhoto);

        Intent myintent = getIntent();
        String nrSalla = myintent.getStringExtra("nrSalla");
        Log.d("nrSalla", nrSalla);


        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("@mipmap/" + "salla" + nrSalla, null, c.getPackageName());
        imgKlasa.setImageResource(id);

        ListView list = (ListView) findViewById(R.id.list);
        listComments = (ListView) findViewById(R.id.listComments);
        content = (EditText) findViewById(R.id.editText);
        objDB = new Databaza(this);

        // Qetu mushet kursori
        Cursor lectureCursor = objDB.getTodayLectures(nrSalla);
        Cursor commentsCursor = objDB.getClassComments(nrSalla);

        Log.v("Klasat", String.valueOf(lectureCursor.getCount()));
        Log.v("Klasat", String.valueOf(commentsCursor.getCount()));

        if (lectureCursor.getCount() > 0) {
            lectureCursorAdapter todoAdapter = new lectureCursorAdapter(this, lectureCursor);
            list.setAdapter(todoAdapter);
        }

        if (commentsCursor.getCount() > 0) {
            commentCursorAdapter todoAdapter = new commentCursorAdapter(this, commentsCursor);
            listComments.setAdapter(todoAdapter);
        }
    }

    public void btnAddOnClick(@SuppressWarnings("UnusedParameters") View v) {
        if (isNetworkAvailable()) {
            String strContent = content.getText().toString();
            content.setText("");
            new InsertComment().execute("nrSalla", strContent);
            Toast.makeText(this, "Successfully posted comment!", Toast.LENGTH_SHORT).show();
            new RetrieveComments().execute();
        }else
        {
            Toast.makeText(this, "Please connect to the internet to post comments.", Toast.LENGTH_LONG).show();
        }
        }

    private class InsertComment extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strparams) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(commentDBURL);
            urlString.append(strparams[0]);
            urlString.append("&commentcontent=");
            try {
                strparams[1] = URLEncoder.encode(strparams[1], "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            urlString.append(strparams[1]);
            Log.d("URL", urlString.toString());

            // Execute URL here...

            HttpURLConnection objURLConnection = null;
            URL objURL;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (objURLConnection != null) {
                objURLConnection.disconnect();
            }
            return null;
        }
    }


    public class RetrieveComments extends AsyncTask<Void, Void, JSONArray> {

        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            StringBuilder urlString = new StringBuilder();
            urlString.append(commentDBURL2);

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

        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            if (this.mException != null) {
                Log.e("JSON Exception", this.mException.toString());
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObjectLecture = result.getJSONObject(i);
                    int commentID = jsonObjectLecture.getInt("id");
                    String commentClassroom = jsonObjectLecture.getString("classnumber");
                    String commentContent = jsonObjectLecture.getString("commentcontent");
                    String reg_date = jsonObjectLecture.getString("reg_date");
                    objDB.insertCommentOrIgnore(commentID, commentClassroom, commentContent,reg_date);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Cursor commentsCursor = objDB.getClassComments("nrSalla");
            if (commentsCursor.getCount()>0) {
                commentCursorAdapter todoAdapter = new commentCursorAdapter(Klasat.this, commentsCursor);
//                commentCursorAdapter todoAdapter = new commentCursorAdapter(class401Activity.this, commentsCursor);
                listComments.setAdapter(todoAdapter);
            }
        }
    }

        public class lectureCursorAdapter extends CursorAdapter {
            lectureCursorAdapter(Context context, Cursor cursor) {
                super(context, cursor, 0);
            }

            // The newView method is used to inflate a new view and return it,
            // you don't bind any data to the view at this point.
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.class_info, parent, false);
            }

            // The bindView method is used to bind all data to a given view
            // such as setting the text on a TextView.
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView txvClassname = (TextView) view.findViewById(R.id.classname);
                TextView txvStarttime = (TextView) view.findViewById(R.id.starttime);
                TextView txvEndtime = (TextView) view.findViewById(R.id.endtime);
                // Extract properties from cursor
                String classname = cursor.getString(cursor.getColumnIndexOrThrow("subject"));
                String starttime = cursor.getString(cursor.getColumnIndexOrThrow("starttime"));
                String endtime = cursor.getString(cursor.getColumnIndexOrThrow("endtime"));
                // Populate fields with extracted properties
                txvClassname.setText(classname);
                txvStarttime.setText(starttime);
                txvEndtime.setText(endtime);
            }
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }

        public class commentCursorAdapter extends CursorAdapter {
            commentCursorAdapter(Context context, Cursor cursor) {
                super(context, cursor, 0);
            }

            // The newView method is used to inflate a new view and return it,
            // you don't bind any data to the view at this point.
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.comment_info, parent, false);
            }

            // The bindView method is used to bind all data to a given view
            // such as setting the text on a TextView.
            @SuppressLint("SetTextI18n") // String resource is extracted
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView txvComment = (TextView) view.findViewById(R.id.txvComment);
                TextView txvDate = (TextView) view.findViewById(R.id.txvDate);
                // Extract properties from cursor
                String content = cursor.getString(cursor.getColumnIndexOrThrow("commentcontent"));
                String reg_date = cursor.getString(cursor.getColumnIndexOrThrow("reg_date"));
                // Populate fields with extracted properties
                txvComment.setText(content);
                txvDate.setText(getString(R.string.commented_on) + reg_date);

            }
        }

}
