package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Kati3 extends AppCompatActivity {

    ImageView imgvKati3 = (ImageView) findViewById(R.id.imgvKati3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kati3);

        imgvKati3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.YELLOW, touch_color, tolerance)) {
                            //Vendoset intenti per me kalu ne klase
                            Intent int301 = new Intent(getApplicationContext(), Klasat.class);
                            int301.putExtra("nrSalla","301");
                            startActivity(int301);
                        }
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Intent int303 = new Intent(getApplicationContext(), Klasat.class);
                            int303.putExtra("nrSalla","303");
                            startActivity(int303);
                        }
                        if (closeMatch(Color.RED, touch_color, tolerance)) {
                            Intent int304 = new Intent(getApplicationContext(), Klasat.class);
                            int304.putExtra("nrSalla","304");
                            startActivity(int304);
                        }
                        if (closeMatch(Color.rgb(255, 0, 255), touch_color, tolerance)) {
                            Intent int310 = new Intent(getApplicationContext(), Klasat.class);
                            int310.putExtra("nrSalla","301");
                            startActivity(int310);
                        }
                        break;
                }
                return true;
            }
        });


    }


    private int getHotspotColor(int x, int y) {
        ImageView img = (ImageView) findViewById(R.id.imgvArea_kati3_click);
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
