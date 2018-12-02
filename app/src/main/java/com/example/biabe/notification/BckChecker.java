package com.example.biabe.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by biabe on 3/29/2017.
 */
class CheckerThread implements Runnable
{
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;

    int _serviceId;
    Context _context;
    public boolean _isRunning=true;
    public void setIsRunning(boolean value)
    {
        _isRunning=value;
    }
    public CheckerThread(int serviceId,Context context)
    {
        _serviceId=serviceId;
        _context=context;

        mPowerManager = (PowerManager) _context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Service");

    }
    void read() {
        HttpBackgroundWorker http=new HttpBackgroundWorker(_context);
        http.execute("");
        //showNotification();
    }
    @Override
    public void run()
    {
        synchronized (this) {
            int count = 0;
            while (_isRunning) {
                try {
                    System.out.println("hey");
                    read();
                    wait(3000);
                    count++;
                    if(count > 1)
                    {
                        //int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

                        //((Activity)_context).getWindow().addFlags(flags);

                        mWakeLock.acquire();
                    }
                } catch (InterruptedException ee) {
                    System.out.println(ee.getMessage());
                }
            }
        }
    }
}
public class BckChecker extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }
    Thread checkThread;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service started");
        checkThread=new Thread(new CheckerThread(startId,getBaseContext()));
        checkThread.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        checkThread.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
