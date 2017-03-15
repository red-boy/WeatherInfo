package com.example.cxy.weatherinfo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cxy.weatherinfo.R;
import com.example.cxy.weatherinfo.view.WeatherView3;

public class Main2Activity extends AppCompatActivity {
    private WeatherView3 mWeatherView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mWeatherView3 = (WeatherView3) findViewById(R.id.pv);
        mWeatherView3.setXCount(3000, 10);//设置
        mWeatherView3.setType(WeatherView3.MONTH_YEAR);//设置图标的类型（月）
        mWeatherView3.setDate(new int[]{1500, 800, 100, 300, 500, 700, 200, 1500, 800, 100, 300, 500});

    }
}
