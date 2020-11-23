package com.example.kruscalprim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView[];
    private float x, y;
    private View myView;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer[] rIdViews = {
                R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
                R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8,
                R.id.imageView9, R.id.imageView10, R.id.imageView11, R.id.imageView12,
                R.id.imageView13, R.id.imageView14
        };
        imageView = new ImageView[14];
        for(int i = 0; i < imageView.length; i++){
            imageView[i] = (ImageView)findViewById(rIdViews[i]);
        }

        myView = findViewById(R.id.relativeLayout);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawCircle(event);
                return true;
            }
        });
    }

    public void drawCircle(MotionEvent event) {
        if(i < 14) {
            x = event.getX();
            y = event.getY();

            Paint circlePaint = new Paint();
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(Color.RED);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(100);

            Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.drawCircle(x, y, 100, circlePaint);
            canvas.drawText(String.valueOf(i/2), x-28, y+40, textPaint);

            imageView[i].setImageBitmap(bitmap);
            Log.e("log", "i: " + i);
            i++;
        }
    }
}