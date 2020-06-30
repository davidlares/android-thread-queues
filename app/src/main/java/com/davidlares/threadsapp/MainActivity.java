package com.davidlares.threadsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ThreadApp";
    private static final String MESSAGE_KEY = "message_key";

    private ProgressBar mProgressBar;
    private Button clickBtn;
    private Handler mHandler;
    private ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // instance
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        clickBtn = (Button) findViewById(R.id.button);

        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling the thread execution
                customThread();
            }
        });

        // Handler instance - receiving the message in the Thread instance
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String message = bundle.getString(MESSAGE_KEY);
                Log.d(TAG, "Here's the message: " + message);
                displayProgressBar(false);
            }
        };

        // Executor instance
        mExecutor = Executors.newFixedThreadPool(5);
    }

    public void customThread() {
        Log.d(TAG, "Starting run");

        // display Bar
        // displayProgressBar(true); // UI related shouldn't be altered when Runnable

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < 10; i++) {
                    Runnable worker = new BackgroundTask(i);
                    mExecutor.execute(worker);
                }

                // Log.d(TAG, "Run: starting thread for 4 secs");
                // try {
                //     Thread.sleep(4000);
                // } catch (InterruptedException e) {
                //    e.printStackTrace();
                // }
                // Log.d(TAG, "Run: ending thread");

                // sending message to the main thread
                // Message message = new Message();
                // Bundle bundle = new Bundle();
                // bundle.putString(MESSAGE_KEY, "Thread is complete");
                // message.setData(bundle);
                // mHandler.sendMessage(message);

                //Log.d(TAG, "Run: runnable complete");
                // displayProgressBar(false); // UI related shouldn't be altered when Runnable
                // try {
                //   Thread.sleep(3000); // sleeping
                //   displayProgressBar(false);
                // } catch (InterruptedException e) {
                //    e.printStackTrace();
                // }
            }
        };

        // executing the Runnable instance via Handlers
        // Handler handler = new Handler();
        // handler.post(runnable);
        // handler.postDelayed(runnable, 3000);

        // using Threads instead
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void displayProgressBar(boolean display) {
        if(display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdown();
    }
}
