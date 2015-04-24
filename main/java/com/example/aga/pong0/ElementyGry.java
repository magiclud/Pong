package com.example.aga.pong0;

import android.graphics.Paint;

/**
 * Created by aga on 24.04.15.
 */
public class ElementyGry {
    protected Paint pole;
    protected  Paint liniePola;
    protected Paint graczNiebPaint;
    protected  Paint graczCzerPaint;
    protected  Paint pilkaPaint;

    protected  float x1GraczNieb;
    protected  float x2GraczNieb;
    protected  float y1GraczNieb;
    protected  float y2GraczNieb;

    protected  float x1GraczCzer;
    protected  float x2GraczCzer;
    protected  float y1GraczCzer;
    protected  float y2GraczCzer;

    protected  float xPilka;
    protected  float yPilka;
    protected  float rPilka;

    protected  int dx = 10;//predkosc
    protected  int dy = 10;

    protected  int punktyCzerwonegoInt=0;
    protected  int punktyNiebieskiegoInt=0;
}
