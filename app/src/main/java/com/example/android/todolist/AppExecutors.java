package com.example.android.todolist;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

    //singleton class
    public class AppExecutors {
        private  static final Object LOCK = new Object();
        private Executor diskIO;
        private Executor networkIO;
        private Executor mainThread;
        private static AppExecutors sInstance;

        public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
            this.diskIO = diskIO;
            this.networkIO = networkIO;
            this.mainThread = mainThread;
        }

        public static AppExecutors getInstance(){
            if (sInstance == null){
                synchronized (LOCK){
                    sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            new MainThreadExecutor());

                }
            }
            return sInstance;
        }

        public Executor getDiskIO() {
            return diskIO;
        }

        public Executor getNetworkIO() {
            return networkIO;
        }

        public Executor getMainThread() {
            return mainThread;
        }

        private static class MainThreadExecutor implements Executor{
            private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable runnable) {
                mainThreadHandler.post(command);
            }
        }

    }

