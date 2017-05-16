package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Kati4 extends AppCompatActivity {

    ImageView imgvKati4 = (ImageView) findViewById(R.id.imgvKati4);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kati4);

        imgvKati4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.rgb(255, 0, 255), touch_color, tolerance)) {
                            //Vendoset intenti per me kalu ne klase
                            Intent int401 = new Intent(getApplicationContext(), Klasat.class);
                            int401.putExtra("nrSalla","401");
                            startActivity(int401);
                        }
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Intent int408 = new Intent(getApplicationContext(), Klasat.class);
                            int408.putExtra("nrSalla","408");
                            startActivity(int408);
                        }
                        if (closeMatch(Color.RED, touch_color, tolerance)) {
                            Intent int411 = new Intent(getApplicationContext(), Klasat.class);
                            int411.putExtra("nrSalla","411");
                            startActivity(int411);
                        }
                        if (closeMatch(Color.YELLOW, touch_color, tolerance)) {
                            Intent int414 = new Intent(getApplicationContext(), Klasat.class);
                            int414.putExtra("nrSalla","414");
                            startActivity(int414);
                        }
                        if (closeMatch(Color.BLACK, touch_color, tolerance)) {
                            Intent int415 = new Intent(getApplicationContext(), Klasat.class);
                            int415.putExtra("nrSalla","415");
                            startActivity(int415);
                        }
                        break;
                }
                return true;
            }
        });


    }


    private int getHotspotColor(int x, int y) {
        ImageView img = (ImageView) findViewById(R.id.imgvArea_kati4_click);
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
