package com.example.biabe.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;
    void loadSharedPreferences()
    {
        SharedPreferences sharedpreferences=getSharedPreferences("notification",Context.MODE_PRIVATE);
        EditText editNotif6=(EditText) findViewById(R.id.editNotif6);
        EditText editNotif7=(EditText) findViewById(R.id.editNotif7);
        EditText editNotif8=(EditText) findViewById(R.id.editNotif8);
        EditText editNotif9=(EditText) findViewById(R.id.editNotif9);
        editNotif6.setText(sharedpreferences.getString("6","problem"));
        editNotif7.setText(sharedpreferences.getString("7","problem"));
        editNotif8.setText(sharedpreferences.getString("8","problem"));
        editNotif9.setText(sharedpreferences.getString("9","problem"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        loadSharedPreferences();
    }

    void read() {
        HttpBackgroundWorker http=new HttpBackgroundWorker(this);
        http.execute("");
        //showNotification();
    }
    public  void showNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.visa);
        mBuilder.setContentTitle("Service started");
        mBuilder.setContentText("Service started!");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

    }
    void onClick(View v) {
        //read();
        //showNotification();
        Intent intent=new Intent(this,BckChecker.class);
        startService(intent);

    }
    void stopService(View v) {
        showNotification();
        Intent intent=new Intent(this,BckChecker.class);
        stopService(intent);

    }
    void saveText(View v)
    {
        EditText editNotif6=(EditText) findViewById(R.id.editNotif6);
        EditText editNotif7=(EditText) findViewById(R.id.editNotif7);
        EditText editNotif8=(EditText) findViewById(R.id.editNotif8);
        EditText editNotif9=(EditText) findViewById(R.id.editNotif9);
        SharedPreferences sharedpreferences=getSharedPreferences("notification",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("6",editNotif6.getText().toString());
        editor.putString("7",editNotif7.getText().toString());
        editor.putString("8",editNotif8.getText().toString());
        editor.putString("9",editNotif9.getText().toString());
        editor.commit();
        loadSharedPreferences();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }

}
