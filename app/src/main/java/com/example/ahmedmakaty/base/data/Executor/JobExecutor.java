package com.example.ahmedmakaty.base.data.Executor;

import android.support.annotation.NonNull;

import com.example.ahmedmakaty.base.domain.executer.ThreadExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class JobExecutor implements ThreadExecutor {

    private static class LazyHolder {
        private static final JobExecutor INSTANCE = new JobExecutor();
    }

    public static JobExecutor getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;

    // Sets the amount of time an idle thread waits before terminating
    private static final long KEEP_ALIVE_TIME = 10;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final LinkedBlockingQueue<Runnable> workQueue;

    private final ThreadPoolExecutor threadPoolExecutor;

    private final ThreadFactory threadFactory;

    private JobExecutor() {
        this.workQueue = new LinkedBlockingQueue();
        this.threadFactory = new JobThreadFactory();
        this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory);
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        this.threadPoolExecutor.execute(runnable);
    }

    private class JobThreadFactory implements ThreadFactory {
        private int counter = 0;
        private static final String THREAD_NAME = "android_";

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, THREAD_NAME + counter++);
        }
    }
}


