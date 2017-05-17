package com.fiek.mali.projektimobiledevelopement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class Kati6 extends AppCompatActivity {

    PhotoView imgvKati6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kati6);
        imgvKati6 = (PhotoView) findViewById(R.id.imgvKati6);

        imgvKati6.setOnTouchListener(new View.OnTouchListener() {
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
                            Intent int602 = new Intent(getApplicationContext(), Klasat.class);
                            int602.putExtra("nrSalla","602");
                            startActivity(int602);
                        }
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Intent int606 = new Intent(getApplicationContext(), Klasat.class);
                            int606.putExtra("nrSalla","606");
                            startActivity(int606);
                        }
                        if (closeMatch(Color.YELLOW, touch_color, tolerance)) {
                            Intent int611 = new Intent(getApplicationContext(), Klasat.class);
                            int611.putExtra("nrSalla","611");
                            startActivity(int611);
                        }
                        if (closeMatch(Color.GREEN, touch_color, tolerance)) {
                            Intent int636 = new Intent(getApplicationContext(), Klasat.class);
                            int636.putExtra("nrSalla","636");
                            startActivity(int636);
                        }
                        if (closeMatch(Color.BLACK, touch_color, tolerance)) {
                            Intent int626 = new Intent(getApplicationContext(), Klasat.class);
                            int626.putExtra("nrSalla","626");
                            startActivity(int626);
                        }
                        if (closeMatch(Color.rgb(0, 255, 255), touch_color, tolerance)) {
                            Intent int621 = new Intent(getApplicationContext(), Klasat.class);
                            int621.putExtra("nrSalla","621");
                            startActivity(int621);
                        }
                        if (closeMatch(Color.WHITE, touch_color, tolerance)) {
                        Intent int616 = new Intent(getApplicationContext(), Klasat.class);
                        int616.putExtra("nrSalla","616");
                        startActivity(int616);
                        }
                        if (closeMatch(Color.rgb(127, 0, 0), touch_color, tolerance)) {
                        Intent int615 = new Intent(getApplicationContext(), Klasat.class);
                        int615.putExtra("nrSalla","615");
                        startActivity(int615);
                        }
                        break;
                }
                return true;
            }
        });


    }


    private int getHotspotColor(int x, int y) {
        PhotoView img = (PhotoView) findViewById(R.id.imgvArea_kati6_click);
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
