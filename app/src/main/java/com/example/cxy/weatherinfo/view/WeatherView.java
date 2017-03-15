package com.example.cxy.weatherinfo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HaodaHw on 2017/3/10.
 * 第一个自定义View:简单的绘制折线
 */

public class WeatherView extends View {
    private final static String X_KEY = "Xpos";
    private final static String Y_KEY = "Ypos";

    private List<Map<String, Integer>> mListPoint = new ArrayList<Map<String, Integer>>();
    Paint mPaint = new Paint();

    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);//设置画笔颜色
        mPaint.setAntiAlias(true);

        for (int index = 0; index < mListPoint.size() - 1; index++) {
            /**drawLine()方法：五个参数，前四个表示起始点及终止点的X/Y值，用于绘制直线，第五个参数是画笔*/
            canvas.drawLine(mListPoint.get(index).get(X_KEY), mListPoint.get(index).get(Y_KEY),
                    mListPoint.get(index + 1).get(X_KEY), mListPoint.get(index + 1).get(Y_KEY), mPaint);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));// 抗锯齿
        }
    }

    /**
     * @param curX which x position you want to draw.
     * @param curY which y position you want to draw.
     */
    public void setLinePoint(int curX, int curY) {
        Map<String, Integer> temp = new HashMap<String, Integer>();
        temp.put(X_KEY, curX);
        temp.put(Y_KEY, curY);
        mListPoint.add(temp);
        invalidate();
    }


}
