package com.example.biabe.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by biabe on 3/29/2017.
 */

public class BckChecker extends Service {
    final class CheckerThread implements Runnable
    {
        int _serviceId;
        public CheckerThread(int serviceId)
        {
            _serviceId=serviceId;
        }
        @Override
        public void run()
        {
            synchronized (this) {
                while (true) {
                    System.out.println("hey");
                    try {
                        wait(1000);
                    } catch (InterruptedException ee) {
                        System.out.println(ee.getMessage());
                    }
                }
            }
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service started");
        Thread checkThread=new Thread(new CheckerThread(startId));
        checkThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
