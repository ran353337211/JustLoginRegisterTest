package com.example.justloginregistertest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtMainLogout;
    private Button mrefresh;
    private TextView time;
    private TextView temperature;
    private TextClock mTextClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件对象
        mBtMainLogout.findViewById(R.id.bt_main_logout);
        // 绑定点击监听器
        mBtMainLogout.setOnClickListener(this);

        mrefresh.findViewById(R.id.refresh);

        time.findViewById(R.id.time);
        temperature.findViewById(R.id.temperature);

        new TimeThread().start(); //启动新的线程
        //mTextClock = (TextClock)findViewById(R.id.textClock);
        // 设置24时制显示格式
        //mTextClock.setFormat24Hour("yyyy-MM-dd hh:mm, EEEE");
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bt_main_logout) {
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);

        }
    }

    //在主线程里面处理消息并更新UI界面
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    time.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                    break;
            }
            return false;
        }
    });
}
