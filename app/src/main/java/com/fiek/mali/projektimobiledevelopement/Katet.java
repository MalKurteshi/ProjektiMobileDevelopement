package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Katet extends AppCompatActivity {

    Button btnKati2;
    Button btnKati3;
    Button btnKati4;
    Button btnKati5;
    Button btnKati6;
    Button btnKati7;
    String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katet);

        btnKati2 = (Button) findViewById(R.id.btnKati2);
        btnKati3 = (Button) findViewById(R.id.btnKati3);
        btnKati4 = (Button) findViewById(R.id.btnKati4);
        btnKati5 = (Button) findViewById(R.id.btnKati5);
        btnKati6 = (Button) findViewById(R.id.btnKati6);
        btnKati7 = (Button) findViewById(R.id.btnKati7);

        // Firebase user
        Intent myintent = getIntent();
        username = myintent.getStringExtra("username");
        Log.v("Katet.java", username);

        //Intentat per kalimin neper planimetrite e kateve me rastin e klikimit mbi button
        btnKati2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin2 = new Intent(getApplicationContext(), Kati2.class);
                startActivity(objKaloNeKatin2);
            }
        });

        btnKati3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin3 = new Intent(getApplicationContext(), Kati3.class);
                startActivity(objKaloNeKatin3);
            }
        });
        btnKati4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin4 = new Intent(getApplicationContext(), Kati4.class);
                objKaloNeKatin4.putExtra("username",username);
                startActivity(objKaloNeKatin4);
            }
        });
        btnKati5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin5 = new Intent(getApplicationContext(), Kati5.class);
                startActivity(objKaloNeKatin5);
            }
        });
        btnKati6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin6 = new Intent(getApplicationContext(), Kati6.class);
                startActivity(objKaloNeKatin6);
            }
        });
        btnKati7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin7 = new Intent(getApplicationContext(), Kati7.class);
                startActivity(objKaloNeKatin7);
            }
        });
    }
}
