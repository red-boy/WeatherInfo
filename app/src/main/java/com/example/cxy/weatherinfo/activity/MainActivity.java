package com.example.cxy.weatherinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cxy.weatherinfo.R;
import com.example.cxy.weatherinfo.view.WeatherView;

public class MainActivity extends AppCompatActivity {
    private WeatherView mWeatherView;
    private static final int MSG_DATA_CHANGE = 0x11;
    private Handler mHandler;
    private int mX = 0;
    private Button mButton;

    private int temperature[] = new int[]{30, 10, 30, 10, 30, 10, 30, 10, 30, 10};//临时的温度数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherView = (WeatherView) findViewById(R.id.weatherView);
        mButton = (Button) findViewById(R.id.btnJumpTo);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "页面跳转");
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case MSG_DATA_CHANGE:
                        mWeatherView.setLinePoint(msg.arg1, msg.arg2);
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        new Thread() {
            public void run() {
                for (int index = 0; index < 10; index++) {
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = mX;
//                    int numTemp = (int) (Math.random() * 200);
//                    message.arg2 = numTemp;
                    message.arg2 = temperature[index];
                    mHandler.sendMessage(message);
                    int num = index + 1;
                    Log.d("MainActivity", "第" + num + "个温度数据:" + temperature[index]);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            }

        }.start();


    }
}
