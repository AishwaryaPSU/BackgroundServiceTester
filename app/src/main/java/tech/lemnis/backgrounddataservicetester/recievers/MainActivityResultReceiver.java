package tech.lemnis.backgrounddataservicetester.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import tech.lemnis.backgrounddataservicetester.MainActivity;


public class MainActivityResultReceiver extends BroadcastReceiver  {
    MainActivity activity;

    public MainActivityResultReceiver(MainActivity activity){
        this.activity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Info", "Message Recieved");
    }


}
