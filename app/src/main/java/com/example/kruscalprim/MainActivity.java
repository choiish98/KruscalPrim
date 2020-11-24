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
    private float x[], y[]; // 마우스 커서 x, y 좌표
    private View myView;
    private int i = 1; // 노드 숫자
    private int j = 1; // 간선 숫자
    private int o = 1; // 간선 그리기 숫자
    private int start, stop, weight;
    private EditText editText, editText2, editText3;
    private Node node[];
    private int parent[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //원 노드 id
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
            lineView[i] = (ImageView)findViewById(rIdViews2[i]);
        }

        // 나머지 id 정리
        editText = findViewById(R.id.editTextNumber);     // 출발점
        editText2 = findViewById(R.id.editTextNumber2);     // 도착점
        editText3 = findViewById(R.id.editTextNumber3);   // 가중치
        node = new Node[20];
        parent = new int[20];

        Button button = findViewById(R.id.button);     // 입력
        x = new float[14];
        y = new float[14];

        // 화면 터치 시
        myView = findViewById(R.id.relativeLayout);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    drawCircle(event);
                return true;
            }
        });

        // 버튼 클릭 시
        button.setOnClickListener(onClickListener);
        findViewById(R.id.interResultBtn).setOnClickListener(onClickListener);
        findViewById(R.id.finalResultBtn).setOnClickListener(onClickListener);
        findViewById(R.id.primInterResultBtn).setOnClickListener(onClickListener);
        findViewById(R.id.primFinalResultBtn).setOnClickListener(onClickListener);
    }

    // 버튼 클릭 함수
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case (R.id.button):
                    start = Integer.parseInt(editText.getText().toString());
                    stop = Integer.parseInt(editText2.getText().toString());
                    weight = Integer.parseInt(editText3.getText().toString());

                    drawLine();
                    break;
                case (R.id.interResultBtn):
                    result(R.id.interResultBtn);
                    break;
                case (R.id.finalResultBtn):
                    result(R.id.finalResultBtn);
                    break;
                case (R.id.primInterResultBtn):
                    //result(R.id.finalResultBtn);
                    break;
                case (R.id.primFinalResultBtn):

                    break;
            }
        }
    };

    // 원 그리는 함수
    public void drawCircle(MotionEvent event) {
        x[i] = event.getX();
        y[i] = event.getY();

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.RED);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);
        Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(x[i], y[i], 100, circlePaint);
        canvas.drawText(String.valueOf(i), x[i] - 28, y[i] + 40, textPaint);
        circleView[i].setImageBitmap(bitmap);
        i++;
    }

    // 간선 그리는 함수
    public void drawLine() {
        Paint linePaint = new Paint();
        linePaint.getStrokeWidth();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5f);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);

        Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawLine(x[start], y[start], x[stop], y[stop], linePaint);
        canvas.drawText(String.valueOf(weight), (x[start] + x[stop]) / 2, (y[start] + y[stop]) / 2, textPaint);
        lineView[j].setImageBitmap(bitmap);

        node[j] = new Node(start, stop, weight);
        j++;
    }

    // 중간 결과
    void result(int buttonName) {
        // 부모 노드 초기화
        for(int k = 0; k < i; k++) {
            parent[k] = k;
        }

        // node sort
        for(int i = 1; i < j; i++){
            for(int k = 1; k < j; k++){
                if(node[i].weight < node[k].weight){
                    int temp1 = node[i].start; node[i].start = node[k].start; node[k].start = temp1;
                    int temp2 = node[i].stop; node[i].stop = node[k].stop; node[k].stop = temp2;
                    int temp3 = node[i].weight; node[i].weight = node[k].weight; node[k].weight = temp3;
                }
            }
        }

        // circleView & lineView Gone
        for(int i = 0; i < circleView.length; i ++) {
            circleView[i].setVisibility(View.GONE);
        }
        for(int i = 0; i < lineView.length; i ++){
            lineView[i].setVisibility(View.GONE);
        }

        // print kruskal algorithms
        int kkagy;
        if(buttonName == R.id.interResultBtn){
            kkagy = j/2;
        } else {
            kkagy = j;
        }
        for(int i = 1; i < kkagy; i++) {
            // 싸이클이 아닐 때
            if(!findParent(parent, node[i].start, node[i].stop)) {
                // 부모를 합침
                unionParent(parent, node[i].start, node[i].stop);

                // 노드 출력
                circleView[node[i].start].setVisibility(View.VISIBLE);
                circleView[node[i].stop].setVisibility(View.VISIBLE);

                // 간선 출력
                Paint linePaint = new Paint();
                linePaint.getStrokeWidth();
                linePaint.setColor(Color.BLACK);
                linePaint.setStrokeWidth(5f);

                Paint textPaint = new Paint();
                textPaint.setColor(Color.BLACK);
                textPaint.setTextSize(50);

                Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                canvas.drawLine(x[node[i].start], y[node[i].start], x[node[i].stop], y[node[i].stop], linePaint);
                canvas.drawText(String.valueOf(node[i].weight), (x[node[i].start] + x[node[i].stop]) / 2, (y[node[i].start] + y[node[i].stop]) / 2, textPaint);
                lineView[o].setImageBitmap(bitmap);
                lineView[o].setVisibility(View.VISIBLE);
                o++;
            }
        }
    }

    // 부모 노드를 찾는 함수
    int getParent(int parent[], int x) {
        if(parent[x] == x) return x;
        return parent[x] = getParent(parent, parent[x]);
    }

    // 부모 합치기
    void unionParent(int parent[], int a, int b) {
        a = getParent(parent, a);
        b = getParent(parent, b);
        if(a < b) parent[b] = a;
        else parent[a] = b;
    }

    // 같은 부모를 가지는지 확인
    boolean findParent(int parent[], int a, int b) {
        a = getParent(parent, a);
        b = getParent(parent, b);
        if(a == b) return true;
        return false;
    }
}