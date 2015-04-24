package com.example.aga.pong0;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


/**
 * Created by aga on 14.04.15.
 */
public class GraWidok extends View {

    private MainActivity mainActivity;
    private ElementyGry element;
    private Handler handler;

    //AttributeSet is used when I create xml layout in

    public GraWidok(final Context context/*, AttributeSet attrs*/) {
        super(context/*, attrs*/);
        Log.d("Cykl Zycia ","graWidok");
        mainActivity = (MainActivity)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        element = new ElementyGry();
        init();

        handler = new Handler(Looper.getMainLooper()) {
        };
    }


    private void init() {
        Log.d("Cykl Zycia ","init");
        element.x1GraczNieb = 8;
        element.x2GraczNieb = getHeight()/2 -100;
        element.y1GraczNieb = 50;
        element.y2GraczNieb = 150;

        element.x1GraczCzer = getWidth()-58;
        element. x2GraczCzer = getHeight()/2-100;
        element.y1GraczCzer = 50;
        element.y2GraczCzer = 150;

        element.xPilka =getWidth()/2;
        element.yPilka = getHeight()/2;
        element.rPilka = 30;

        element.pole = new Paint();
        element.pole.setColor(getResources().getColor(R.color.zolty));
        element.liniePola = new Paint();
        element.liniePola.setColor(Color.WHITE);
        element.liniePola.setStyle(Paint.Style.STROKE);
        element.liniePola.setStrokeWidth(16f);

        element.pilkaPaint = new Paint();
        element.pilkaPaint.setStyle(Paint.Style.FILL);
        element.pilkaPaint.setColor(Color.MAGENTA);

        element.graczNiebPaint = new Paint();
        element. graczNiebPaint.setTextSize(100);
        element.graczNiebPaint.setColor(Color.BLUE);

        element.graczCzerPaint = new Paint();
        element.graczCzerPaint.setTextSize(100);
        element.graczCzerPaint.setColor(Color.RED);
    }

    // metoda wywoluje sie na poczatku przed tworzeniem widoku
protected void onSizeChanged(int w, int h, int oldw, int oldh){
     Log.d("Cykl Zycia ","onSizeChanged");

     init();// <- ustawienia kolorow, itp. wartosci poczatkowe
}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), element.pole);
        canvas.drawRect(0,0,getWidth(),getHeight(),element.liniePola);

        canvas.drawLine(getWidth()/2, 0, getWidth()/2 , getWidth(), element.liniePola);

        canvas.drawCircle(element.xPilka, element.yPilka, element.rPilka, element.pilkaPaint);

        canvas.drawRect(element.x1GraczNieb, element.x2GraczNieb, element.x1GraczNieb+element.y1GraczNieb, element.x2GraczNieb+element.y2GraczNieb, element.graczNiebPaint);
        canvas.drawText(String.valueOf(element.punktyNiebieskiegoInt), getWidth() / 2 - 100, 100, element.graczNiebPaint);

        canvas.drawRect(element.x1GraczCzer, element.x2GraczCzer, element.x1GraczCzer+element.y1GraczCzer, element.x2GraczCzer+element.y2GraczCzer, element.graczCzerPaint);
        canvas.drawText(String.valueOf(element.punktyCzerwonegoInt), getWidth()/2 + 40, 100, element.graczCzerPaint);


        aktualizujPolozenieKulki();

        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();

            }
        });
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //        paint.setColor(Color.RED);
//        Path path = new Path();
//        path.addCircle(x, y, 40, Path.Direction.CW);
//        canvas.drawPath(path,paint);
    }

    private void aktualizujPolozenieKulki() {
        element.xPilka += element.dx;
        element.yPilka += element.dy;
        sprawdzCzyPunktDlaNiebieskiego();
        sprawdzCzyPunktDlaCzerwonego();
        sprawdzCzyOdbicOdBocznejSciany();
    }

    private void sprawdzCzyOdbicOdBocznejSciany() {
        if(element.yPilka > getHeight()-40 || element.yPilka <40){
            element.dy= element.dy* (-1);
        }
    }

    private void sprawdzCzyPunktDlaCzerwonego() {
        if(element.xPilka <element.y1GraczCzer+element.rPilka){
            if(element.yPilka< element.x2GraczNieb || element.yPilka> element.x2GraczNieb+element.y2GraczNieb){
                // Log.d("odbijPilke ","PILKA jest na klocku niebieskim!");
                element.punktyCzerwonegoInt++;
                init();
            }
            element.dx = element.dx*(-1);
        }
    }

    private void sprawdzCzyPunktDlaNiebieskiego() {
        if(element.xPilka > getWidth()-(element.y1GraczCzer+element.rPilka) ){
           if(element.yPilka< element.x2GraczCzer || element.yPilka> element.x2GraczCzer+element.y2GraczCzer){
                //  Log.d("aktualizujPolozenieKulki ","PILKA jest na krancu  czerwonego!");
               element.punktyNiebieskiegoInt++;
                init();
            }
            element. dx = element.dx*(-1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionPeformed = event.getActionMasked();


        if (actionPeformed == MotionEvent.ACTION_MOVE) {
            int touchCounter = event.getPointerCount();

            for (int t = 0; t < touchCounter; t++) { // MULTITOUCH :)
                int x;
                int y;
                int id = event.findPointerIndex(t);
//                  Log.d("touchCounter, ", ""+ touchCounter+",  id: "+id);
                if(id <0){
                    x = (int) event.getX();
                    y = (int) event.getY();
                }else {

                    x = (int) event.getX(id);
                    y = (int) event.getY(id);
                }
                if(x<getWidth()/2 ) {
                    przesunNiebieski(y);
                }
                if(x>getWidth()/2 ) {
                    przesunCzerwony(y);
                }
            }
        }

    return true; //jak bedzie false to pomija
    }

    private void przesunCzerwony(float y) {
        element.x2GraczCzer = y;
        if(element.x2GraczCzer<8){
            element.x2GraczCzer = 8;
        }
        if(element.x2GraczCzer>getHeight()-element.y2GraczCzer-8){
            element.x2GraczCzer = getHeight()-element.y2GraczCzer-8;
        }
    }

    private void przesunNiebieski(float y) {
        element.x2GraczNieb=y;
        if(element.x2GraczNieb<8){
            element.x2GraczNieb = 8;
        }
        if(element.x2GraczNieb>getHeight()-element.y2GraczNieb-8){
            element.x2GraczNieb = getHeight()-element.y2GraczNieb-8;
        }
    }

}
