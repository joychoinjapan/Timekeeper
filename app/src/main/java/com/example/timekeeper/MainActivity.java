package com.example.timekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView timer,txt,title;
    private ImageView button;
    private Handler handler ;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        txt = findViewById(R.id.txt);
        timer = findViewById(R.id.timer);
        button = findViewById(R.id.button);
        status = false;

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(false == status){
                    status = true;
                    Thread th1 =new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            int i = 1;
                            while (status){
                                try {
                                    sleep(1000);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                Message message = new Message();
                                message.arg1 = i;
                                handler.sendMessage(message);
                                i++;
                            }
                        }
                    };
                    th1.start();
                    title.setText("工作中.....");
                    button.setImageResource(R.mipmap.stop);
                }else{
                    status = false;
                }

            }
        });

       handler = new Handler(){
           @Override
           public void handleMessage(@NonNull Message msg) {
               super.handleMessage(msg);
               int hour = msg.arg1/3600;
               int min = msg.arg1/60;
               int sec = msg.arg1%60;
               //00:00
               String hour_s =hour<10?"0"+hour:""+hour;
               String min_s = min<10?"0"+min:""+min;
               String sec_s = sec<10?"0"+sec:""+sec;
               String res ;
               if(hour>0){
                   res = hour_s+":"+min_s+":"+sec_s;
                   timer.setText(res);
               }else{
                   res = min_s+":"+sec_s;
                   timer.setText(res);
               }

              if(false == status){
                  title.setText("已停止");
                  button.setImageResource(R.mipmap.start);
                  txt.setText("用时："+res+"秒");
              }
           }
       };







    }
}