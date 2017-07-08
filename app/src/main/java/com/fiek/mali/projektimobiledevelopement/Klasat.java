package com.fiek.mali.projektimobiledevelopement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.github.chrisbanes.photoview.PhotoView;

public class Klasat extends AppCompatActivity {

    // Local class variables
    private ListView listComments;
    private EditText content;
    private Databaza objDB;
//    private static final String commentDBURL2 = "http://200.6.254.247/comments.php?t=0";
//    private static final String commentDBURL = "http://200.6.254.247/comments.php?t=1&classroom=";
    PhotoView imgKlasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasat);
        imgKlasa = (PhotoView) findViewById(R.id.imgvClassPhoto);

        Intent myintent = getIntent();
        String nrSalla =myintent.getStringExtra("nrSalla");
        Log.d("nrSalla",nrSalla);


        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("@mipmap/"+"salla"+nrSalla, null, c.getPackageName());
        imgKlasa.setImageResource(id);

        ListView list = (ListView) findViewById(R.id.list);
        listComments = (ListView)findViewById(R.id.listComments);
        content = (EditText)findViewById(R.id.editText);
        objDB = new Databaza(this);

        // Qetu mushet kursori
        Cursor lectureCursor = objDB.getTodayLectures(nrSalla);
        //        Cursor commentsCursor = objDB.getClassComments("401");
        Log.v("Klasat", String.valueOf(lectureCursor.getCount()));

        if (lectureCursor.getCount()>0) {
            lectureCursorAdapter todoAdapter = new lectureCursorAdapter(this, lectureCursor);
            list.setAdapter(todoAdapter);
        }

//        if (commentsCursor.getCount()>0) {
//            commentCursorAdapter todoAdapter = new commentCursorAdapter(this, commentsCursor);
//            listComments.setAdapter(todoAdapter);
//        }
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
}
