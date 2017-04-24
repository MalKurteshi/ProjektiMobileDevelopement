package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnVazhdoPaLogim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVazhdoPaLogim = (Button) findViewById(R.id.btnVazhdoPaLogim);

        btnVazhdoPaLogim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKate = new Intent(getApplicationContext(), Katet.class);
                startActivity(objKaloNeKate);
            }
        });
    }
}
