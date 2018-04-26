package tech.lemnis.backgrounddataservicetester.asynctasks;

import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import tech.lemnis.backgrounddataservicetester.callbacks.AsyncCallback;
import tech.lemnis.remotebackgroundservice.services.IAsyncCallback;
import tech.lemnis.remotebackgroundservice.services.IRemoteService;
import tech.lemnis.backgrounddataservicetester.MainActivity;


public class DataServiceInvokerAsyncTask extends AsyncTask<String,String,String> {
    IRemoteService service;
    MainActivity activity;
    int maxRetries = 5;

    public DataServiceInvokerAsyncTask(IRemoteService service, MainActivity activity){
        this.service = service;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String[] objects) {
        Log.i("Info", "AsyncTask started");
        String largeString = objects[0].toString();
        final int viewToUpdate = Integer.parseInt(objects[1]);
        AsyncCallback.Stub asyncCallback = new AsyncCallback.Stub() {
            @Override
            public void handleResponse(final String name) throws RemoteException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.updateViews(Double.parseDouble(name), viewToUpdate);
                    }
            });
            }
        };
        Log.i("Info", String.format("largeString Length %s ", largeString.length()));
        int delay = Integer.parseInt(objects[2]);
        int retry = 0;
        try {
            sendDataToBackGroundService(largeString, viewToUpdate, delay, asyncCallback, retry);
        } catch (InterruptedException interruptedException){
            Log.e("Error", interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
        return "";
    }
   // Note: Adding Retries to implement a reliable communication between service and client.
    private void sendDataToBackGroundService(String largeString, int viewToUpdate, int delay, IAsyncCallback asyncCallback,int retry) throws InterruptedException {
        if(retry <= maxRetries) {
            try {
                service.doSomethingAsync(largeString, viewToUpdate, delay, asyncCallback);
            } catch (Exception remoteException) {
            Log.e("Error", remoteException.getMessage());
            remoteException.printStackTrace();
            // wait 4 seconds between each retries. Use exponential backOff for better retries.
            Thread.sleep(4000);
            Log.e("Info", "Retrying.....retry count : " + retry);
            sendDataToBackGroundService(largeString,viewToUpdate,delay,asyncCallback,retry+1);
          }
        }
    }
}
