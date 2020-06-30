package com.davidlares.threadsapp;

import android.util.Log;

public class BackgroundTask implements Runnable {

    private static final String TAG = "ThreadApp";
    private int threadNumber;

    public BackgroundTask(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        Log.d(TAG, Thread.currentThread().getName() + "start. Thread number = " + threadNumber);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, Thread.currentThread().getName() + "end. Thread number = " + threadNumber);

    }
}
