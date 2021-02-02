package com.example.newtimekepper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class newTimeKeeper extends AppCompatActivity {

    private TextView timer,txt,title;
    private ImageView button;
    private Handler handler ;
    private boolean status;
    private int i;
    private Runnable runnable;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_keeper);
        title = findViewById(R.id.title);
        txt = findViewById(R.id.txt);
        timer = findViewById(R.id.timer);
        button = findViewById(R.id.button);
        flag = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable,1000);
                i++;
                String res = change(i);
                timer.setText(res);
            }
        };



        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            Thread thread1 = new Thread(){
                @Override
                public void run(){
                    super.run();
                    i = 0;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText("00:00");
                            handler.postDelayed(runnable, 1000);
                        }
                    });

                }
            };

            flag = !flag;
            if(flag) {
                button.setImageResource(R.mipmap.stop);
                title.setText("正在工作");
                thread1.start();
            }else{
                thread1.interrupt();
                handler.removeCallbacks(runnable);
                String res = change(i);
                button.setImageResource(R.mipmap.start);
                title.setText("计时器");
                Log.e("RES",res);
                txt.setText("用时："+res+"秒");
            }
            }
        });





//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
////                        try {
////                            sleep(2000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//
//                        /**
//                         * runOnThread运行机制：判断当前线程是主线程；直接执行Runnable的run方法
//                         * 不是主线程，由handler调用post方法
//                         *
//                         */
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                button.setImageResource(R.mipmap.stop);
////                            }
////                        });
//
//
//                        //在post方法中，(即使在子线程中)我们可以处理一切和视图相关的操作
//                        //post即为在子线程中将操作嫁接到主线程
////                        handler.post(new Runnable() {
////                            @Override
////                            public void run() {
////                                button.setImageResource(R.mipmap.stop);
////                            }
////                        });
//
//                        /**
//                         * 延迟3000毫秒执行任务
//                         */
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        button.setImageResource(R.mipmap.stop);
//                                    }
//                                });
//
//                            }
//                        },3000);
//
//                        handler.postAtTime(new Runnable() {
//                            @Override
//                            public void run() {
//                                button.setImageResource(R.mipmap.start);
//                            }
//                        }, SystemClock.uptimeMillis()+6000);
//                    }
//                }.start();
//
//
//            }
//        });

    }

    private String change(int i) {
        int hour = i/3600;
        int min = i/60;
        int sec = i%60;
        //00:00
        String hour_s =hour<10?"0"+hour:""+hour;
        String min_s = min<10?"0"+min:""+min;
        String sec_s = sec<10?"0"+sec:""+sec;
        String res ;

        if(hour>0){
            res = hour_s+":"+min_s+":"+sec_s;
        }else{
            res = min_s+":"+sec_s;
        }

        return res;
    }
}