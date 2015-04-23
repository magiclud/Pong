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



/**
 * Created by aga on 14.04.15.
 */
public class GraWidok extends View {

    MainActivity mainActivity;

    private Paint pole;
    private Paint liniePola;
    private Paint graczNiebPaint;
    private Paint graczCzerPaint;
    private Paint pilkaPaint;

    private float x1GraczNieb;
    private float x2GraczNieb;
    private float y1GraczNieb;
    private float y2GraczNieb;

    private float x1GraczCzer;
    private float x2GraczCzer;
    private float y1GraczCzer;
    private float y2GraczCzer;

    private float xPilka;
    private float yPilka;
    private float rPilka;

    private int dx = 10;//predkosc
    private int dy = 10;

    private int punktyCzerwonegoInt=0;
    private int punktyNiebieskiegoInt=0;

    private Handler handler;


    //AttributeSet is used when I create xml layout in

    public GraWidok(final Context context/*, AttributeSet attrs*/) {
        super(context/*, attrs*/);
        Log.d("Cykl Zycia ","graWidok");
        mainActivity = (MainActivity)context;
        setFocusable(true);
        setFocusableInTouchMode(true);

        init();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler = new Handler(Looper.getMainLooper()) {
                };
            }
        });
        thread.start();
    }


    private void init() {
        Log.d("Cykl Zycia ","init");
        x1GraczNieb = 8;
        x2GraczNieb = getHeight()/2 -100;
        y1GraczNieb = 50;
        y2GraczNieb = 150;

       x1GraczCzer = getWidth()-58;
       x2GraczCzer = getHeight()/2-100;
       y1GraczCzer = 50;
       y2GraczCzer = 150;

        xPilka =getWidth()/2;
        yPilka = getHeight()/2;
        rPilka = 30;

        pole = new Paint();
        pole.setColor(getResources().getColor(R.color.zolty));
        liniePola = new Paint();
        liniePola.setColor(Color.WHITE);
        liniePola.setStyle(Paint.Style.STROKE);
        liniePola.setStrokeWidth(16f);

        pilkaPaint = new Paint();
        pilkaPaint.setStyle(Paint.Style.FILL);
        pilkaPaint.setColor(Color.MAGENTA);

        graczNiebPaint = new Paint();
        graczNiebPaint.setTextSize(100);
        graczNiebPaint.setColor(Color.BLUE);

        graczCzerPaint = new Paint();
        graczCzerPaint.setTextSize(100);
        graczCzerPaint.setColor(Color.RED);
    }

    // metoda wywoluje sie na poczatku przed tworzeniem widoku, cos a'la konstruktir
protected void onSizeChanged(int w, int h, int oldw, int oldh){
    Log.d("Cykl Zycia ","onSizeChanged");

     init();// <- ustawienia kolorow, itp. wartosci poczatkowe
}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), pole);
        canvas.drawRect(0,0,getWidth(),getHeight(),liniePola);

        canvas.drawLine(getWidth()/2, 0, getWidth()/2 , getWidth(), liniePola);

        canvas.drawCircle(xPilka, yPilka, rPilka, pilkaPaint);

        canvas.drawRect(x1GraczNieb, x2GraczNieb, x1GraczNieb+y1GraczNieb, x2GraczNieb+y2GraczNieb, graczNiebPaint);
        canvas.drawText(String.valueOf(punktyNiebieskiegoInt), getWidth() / 2 - 100, 100, graczNiebPaint);

        canvas.drawRect(x1GraczCzer, x2GraczCzer, x1GraczCzer+y1GraczCzer, x2GraczCzer+y2GraczCzer, graczCzerPaint);
        canvas.drawText(String.valueOf(punktyCzerwonegoInt), getWidth()/2 + 40, 100, graczCzerPaint);


        aktualizujPolozenieKulki();

        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        //        paint.setColor(Color.RED);
//        Path path = new Path();
//        path.addCircle(x, y, 40, Path.Direction.CW);
//        canvas.drawPath(path,paint);
    }

    private void aktualizujPolozenieKulki() {
        zmienPolozenieKulki();
        if(xPilka > getWidth()-(y1GraczCzer+rPilka) ){

            if(yPilka< x2GraczCzer || yPilka> x2GraczCzer+y2GraczCzer){  //TODO napisz to w jedyn if i juz

                //  Log.d("aktualizujPolozenieKulki ","PILKA jest na krancu  czerwonego!");
                  punktyNiebieskiegoInt++;
                  init();

             // }
            }
            dx = dx*(-1);
       }
        if(xPilka <y1GraczCzer+rPilka){
            if(yPilka< x2GraczNieb || yPilka> x2GraczNieb+y2GraczNieb){
               // Log.d("odbijPilke ","PILKA jest na klocku niebieskim!");
                punktyCzerwonegoInt++;
                init();
            }
            dx = dx*(-1);

        }
        if(yPilka > getHeight()-40 || yPilka <40){
            dy= dy* (-1);
        }
    }

    public void zmienPolozenieKulki(){
        xPilka += dx;
        yPilka += dy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("Cykl Zycia ","onTouchEvent");

        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (action == MotionEvent.ACTION_MOVE) {
            if(x<getWidth()/2 ) {
                przesunNiebieski(y);
            }
            if(x>getWidth()/2 ) {
                przesunCzerwony(y);
            }
            invalidate();
        }
        return true; //jak bedzie false to pomija
    }

    private void przesunCzerwony(float y) {
        x2GraczCzer = y;
        if(x2GraczCzer<8){
            x2GraczCzer = 8;
        }
        if(x2GraczCzer>getHeight()-y2GraczCzer-8){
            x2GraczCzer = getHeight()-y2GraczCzer-8;
        }
    }

    private void przesunNiebieski(float y) {
        x2GraczNieb=y;
        if(x2GraczNieb<8){
            x2GraczNieb = 8;
        }
        if(x2GraczNieb>getHeight()-y2GraczNieb-8){
            x2GraczNieb = getHeight()-y2GraczNieb-8;
        }
    }

}
