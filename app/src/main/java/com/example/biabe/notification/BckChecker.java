package com.example.biabe.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by biabe on 3/29/2017.
 */
class CheckerThread implements Runnable
{
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
            while (_isRunning) {
                try {
                    System.out.println("hey");
                    read();
                    wait(3000);
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
    public  void showNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext());
        mBuilder.setSmallIcon(R.drawable.visa);
        mBuilder.setContentTitle("Not frin background");
        mBuilder.setContentText("it works!");

        NotificationManager mNotificationManager = (NotificationManager)getBaseContext().getSystemService(NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

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
