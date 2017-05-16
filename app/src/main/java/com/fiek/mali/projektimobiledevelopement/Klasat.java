package com.fiek.mali.projektimobiledevelopement;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.chrisbanes.photoview.PhotoView;

public class Klasat extends AppCompatActivity {

    PhotoView imgKlasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasat);

        Intent myintent = getIntent();
        String nrSalla =myintent.getStringExtra("nrSalla");
        Log.d("nrSalla",nrSalla);

        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+"salla"+nrSalla, null, c.getPackageName());
        ((PhotoView)imgKlasa.findViewById(R.id.imgvClassPhoto)).setImageResource(id);

        // merr orari ne baze te nrSalla
        // merr kommentet ne baze te nrSalla
    }
}
