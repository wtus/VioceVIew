package com.example.wuht.vioceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wuht on 2016/9/5.
 */
public class VioceView extends View {
    private Paint mPaint;
    private float radius = 100;
    private float mProcess = 0;
    private float downY, moveY;
    private float oldVolume;

    public VioceView(Context context) {
        super(context);
        init();
    }

    public VioceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VioceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

       /* new Thread() {
            @Override
            public void run() {
                super.run();
                while (false) {
                    mProcess += 60;
                    postInvalidateDelayed(500);
                    if (mProcess > 360) {
                        mProcess = 0;
                    }
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }

            }
        }.start();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawxBitmap(canvas);
        drawCirCle(canvas);
        drawArc(canvas);
    }

    private void drawxBitmap(Canvas canvas) {
        canvas.save();
        Bitmap vioce = BitmapFactory.decodeResource(getResources(), R.mipmap.vioce);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(vioce, -vioce.getWidth() / 2, -vioce.getHeight() / 2, mPaint);
        canvas.restore();
    }

    private void drawCirCle(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    private void drawArc(Canvas canvas) {
        mPaint.setColor(Color.RED);
        RectF rectF = new RectF(getWidth() / 2 - 100, getHeight() / 2 - 100, getWidth() / 2 + 100, getHeight() / 2 + 100);
        canvas.drawArc(rectF, -90, mProcess, false, mPaint);
    }


    public void addProcess() {
        //if (!isValuable()) return;
        mProcess = Math.min(360, mProcess + 24);
        invalidate();
    }

    public void subProcess() {
        // if (!isValuable()) return;
        mProcess = Math.max(0, mProcess - 24);
        invalidate();
    }

    public void setPcocess(float process) {
        this.mProcess = process * 24;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                oldVolume = mProcess;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY();
                Log.e("VioceView", "moveY:" + (moveY - downY));
                mProcess = (moveY - downY) / getHeight() * 15 * 24;
                if (moveY - downY > 0) {//下滑
                    mProcess = Math.min((moveY - downY) / getHeight() * 15 * 24, 360);
                } else {
                    mProcess = Math.max((moveY - downY) / getHeight() * 15 * 24, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                downY = moveY;
                break;
        }
        invalidate();
        return true;
        //return super.onTouchEvent(event);
    }


}

