package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Kati2 extends AppCompatActivity {

 ImageView imgvKati2 = (ImageView) findViewById(R.id.imgvKati2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kati2);


        imgvKati2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            //Vendoset intenti per me kalu ne klasen 201 nese plotesohet aktiviteti
                            Intent int201 = new Intent(getApplicationContext(), Klasat.class);
                            int201.putExtra("nrSalla","201");
                            startActivity(int201);
                        }

                        break;
                }
                return true;
            }
        });

    }

    private int getHotspotColor(int x, int y) {
        ImageView img = (ImageView) findViewById(R.id.imgvArea_kati2_click);
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



}
