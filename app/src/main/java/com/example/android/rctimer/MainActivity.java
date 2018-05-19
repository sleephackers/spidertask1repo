package com.example.android.rctimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TextView textView;
    int milliseconds;
    boolean running;
    String millis;
    String secs;
    MediaPlayer mediaPlayer;
    int time;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout= findViewById(R.id.root);
        textView=findViewById(R.id.timer);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startTimer();

            }
        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(15000, 1) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                secs=checkDigit((int) (millisUntilFinished/1000));
                millis=checkDigit (( int) ((millisUntilFinished/10)-((millisUntilFinished/1000)*100)));
                textView.setText(secs+":"+ millis);
                if((int) (millisUntilFinished/1000)<=3 && ( int) ((millisUntilFinished/10)-((millisUntilFinished/1000)*100))==0 )
                {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.beep);
                    mediaPlayer.start();
                }

            }

            @Override
            public void onFinish() {
                cancel();
                mediaPlayer.stop();
                starttimer(textView);
            }
        }.start();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mCountDownTimer.cancel();
                starttimer(textView);

            }
        });


    }
    public String checkDigit(int number)
    {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void starttimer(View view){
        running = true;
        runTimer();
    }

    public void stopTimer(View view){
        running = false;
    }


    public void runTimer(){
        final TextView textView = (TextView) findViewById(R.id.timer);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopTimer(textView);
                    }
                });
                int minutes = milliseconds / 3600;
                int sec = (milliseconds % 3600) / 60;
                int mil = milliseconds % 60;
                String time = String.format("%02d:%02d:%02d",minutes, sec,mil);
                textView.setText(time);
                if(running) {
                    milliseconds++;
                }
                handler.postDelayed(this, 1);
            }
        });

    }

}
