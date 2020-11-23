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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView circleView[];
    private ImageView lineView[];
    private float x, y; // 마우스 커서 x, y 좌표
    private View myView;
    private int i = 1; // 노드 숫자
    private int j = 1; // 간선 숫자
    private EditText start;
    private EditText stop;
    private EditText weight;
    private int nodePoint[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 원 노드 id
        Integer[] rIdViews = {
                R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
                R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8,
                R.id.imageView9, R.id.imageView10, R.id.imageView11, R.id.imageView12,
                R.id.imageView13, R.id.imageView14
        };
        circleView = new ImageView[14];
        for(int i = 0; i < circleView.length; i++){
            circleView[i] = (ImageView)findViewById(rIdViews[i]);
        }

        // 간선 id
        Integer[] rIdViews2 = {
                R.id.lineView1, R.id.lineView2, R.id.lineView3, R.id.lineView4,
                R.id.lineView5, R.id.lineView6, R.id.lineView7, R.id.lineView8,
                R.id.lineView9, R.id.lineView10, R.id.lineView11, R.id.lineView12,
                R.id.lineView13, R.id.lineView14
        };
        lineView = new ImageView[14];
        for(int i = 0; i < lineView.length; i++){
            lineView[i] = (ImageView)findViewById(rIdViews[i]);
        }

        // 나머지 id 정리
        start = findViewById(R.id.editTextNumber);     // 출발점
        stop = findViewById(R.id.editTextNumber2);     // 도착점
        weight = findViewById(R.id.editTextNumber3);   // 가중치
        Button button = findViewById(R.id.button);     // 입력
        nodePoint = new int[2];

        // 화면 터치 시
        myView = findViewById(R.id.relativeLayout);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawCircle(event);
                return true;
            }
        });

        // 입력 버튼 클릭 시
        button.setOnClickListener(onClickListener);
    }

    // 버튼 클릭 함수
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.button):
                    drawLine();
                    break;
            }
        }
    };

    // 원 그리는 함수
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
            if(i < 13) {
                canvas.drawText(String.valueOf(i / 2), x - 28, y + 40, textPaint);
            } else {
                canvas.drawText(String.valueOf(7), x - 28, y + 40, textPaint);
            }
            circleView[i].setImageBitmap(bitmap);
            i++;
        }
    }

    // 간선 그리는 함수
    public void drawLine() {
        Paint linePaint = new Paint();
        linePaint.getStrokeWidth();
        linePaint.setColor(Color.BLACK);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawLine(20, 20, 1000, 1000, linePaint);
        canvas.drawText(String.valueOf(weight), 80, 80, textPaint);
        lineView[j].setImageBitmap(bitmap);
        j++;
    }
}