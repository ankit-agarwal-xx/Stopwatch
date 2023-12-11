package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private MaterialButton reset, start, stop;
    private long startTime = 0L, timeBuff = 0L, updateTime = 0L;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        reset = findViewById(R.id.reset);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        start.setOnClickListener(view -> startTimer());
        stop.setOnClickListener(view -> stopTimer());
        reset.setOnClickListener(view -> resetTimer());

        textView.setText("00:00:00");
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(updateTimerThread, 0);
        reset.setEnabled(false);
        stop.setEnabled(true);
        start.setEnabled(false);
    }

    private void stopTimer() {
        timeBuff += System.currentTimeMillis() - startTime;
        handler.removeCallbacks(updateTimerThread);
        reset.setEnabled(true);
        stop.setEnabled(false);
        start.setEnabled(true);
    }

    private void resetTimer() {
        startTime = 0L;
        timeBuff = 0L;
        updateTime = 0L;
        textView.setText("00:00:00");
    }

    private final Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            long timeInMilliseconds = System.currentTimeMillis() - startTime;
            updateTime = timeBuff + timeInMilliseconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            textView.setText(String.format("%02d:%02d:%02d", mins, secs, milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
