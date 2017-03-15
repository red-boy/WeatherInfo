package com.example.cxy.weatherinfo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by HaodaHw on 2017/3/15.
 */

public class WeatherView3 extends View {
    private Paint paintLine, paintPoint, textPaint, linkPaint;
    private int[] data;
    private float left;
    private float bottom;
    public int defaultType = DAY_WEEK;
    private int xLineCount = 10;
    private int yLineCount = 10;
    private float xMaxValue, yMaxValue;
    private float xInterval, yInterval;

    public String[] days = {"昊达315", "���ڶ�", "������", "������", "������", "������", "������"};
    public String[] weeks = {"昊达315", "�ڶ���", "������", "������"};
    public String[] mouths = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",};
    public String[] days_month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    public ArrayList<Float> xPoint = new ArrayList<Float>();

    public String[] defaultDay = days;

    public static final int DAY_WEEK = 0;
    public static final int DAY_MONTH = 1;
    public static final int WEEK_MONTH = 2;
    public static final int MONTH_YEAR = 3;

    public WeatherView3(Context context) {
        super(context);
        init(context);//初始化
    }

    public WeatherView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeatherView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context mcontext) {
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.GRAY);
        paintLine.setFakeBoldText(true);
        paintLine.setStrokeWidth(1.5f);//空心

        paintPoint = new Paint();
        paintPoint.setColor(Color.RED);
        paintPoint.setFakeBoldText(true);//粗体
        paintPoint.setStrokeWidth(20);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(15);

        linkPaint = new Paint();
        linkPaint.setColor(Color.RED);
        linkPaint.setFakeBoldText(true);
        linkPaint.setTextSize(25);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFrame(canvas);//绘制图表的框架：XY轴
//        doDraw(canvas);//根据数据绘制折线
        super.onDraw(canvas);
    }

    private void doDraw(Canvas canvas) {
        float sumHeight = xLineCount * xInterval;
        float tempInterval = xMaxValue / sumHeight;
        for (int i = 0; i < data.length; i++) {
            float x = xPoint.get(i);
            float yPotion = sumHeight - data[i] / tempInterval + xInterval;
            canvas.drawCircle(x, yPotion, 5, paintPoint);
            if (i != data.length - 1) {
                float nextYPotion = sumHeight - data[i + 1] / tempInterval + xInterval;
                canvas.drawLine(x, yPotion, xPoint.get(i + 1), nextYPotion, linkPaint);
            }
        }
    }


    private void drawFrame(Canvas canvas) {
        calculateLeft();

        for (int i = 0; i <= xLineCount; i++) {
            float startY = i * xInterval + xInterval;
            canvas.drawLine(left + 5, startY, getWidth(), startY, paintLine);
            textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(Math.round(xMaxValue / xLineCount * (xLineCount - i) - 0.5) + "", left, startY + bottom / 4, textPaint);
        }

        for (int j = 0; j < yLineCount; j++) {

            float leftSpace = yInterval * j + left + 5;

            canvas.drawLine(leftSpace, bottom, leftSpace, this.getHeight() - bottom, paintLine);
            textPaint.setTextAlign(Paint.Align.CENTER);

            if (j == 0) {
                continue;
            }
            xPoint.add(leftSpace);
            canvas.drawText(defaultDay[j - 1], leftSpace, this.getHeight(), textPaint);

        }
    }

    private void calculateLeft() {
        for (int value : data) {
            float tempLeft = textPaint.measureText(value + "");
            if (tempLeft > left) {
                left = tempLeft;

            }
        }
        bottom = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;

        switch (defaultType) {
            case DAY_MONTH:
                yLineCount = getCurrentMonthLastDay() + 1;
                defaultDay = days_month;
                break;
            case DAY_WEEK:
                yLineCount = 8;
                defaultDay = days;
                break;
            case MONTH_YEAR:
                yLineCount = 13;
                defaultDay = mouths;
                break;
            case WEEK_MONTH:
                yLineCount = 5;
                defaultDay = weeks;
                break;
        }

        if (xMaxValue == 0)
            xMaxValue = this.getWidth() - left;

        if (xInterval == 0)
            xInterval = (this.getHeight() - bottom) / (xLineCount + 1);


        if (yMaxValue == 0)
            yMaxValue = this.getHeight() - bottom;

        if (yInterval == 0)
            yInterval = (this.getWidth() - left) / yLineCount;
    }

    private int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//����������Ϊ���µ�һ��
        a.roll(Calendar.DATE, -1);//���ڻع�һ�죬Ҳ�������һ��
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public void setXCount(int maxValue, int count) {
        xLineCount = count;
        xMaxValue = maxValue;
    }

    public void setType(int type) {
        defaultType = type;
    }

    public void setDate(int[] x) {
        data = x;
        invalidate();
    }
}
