package tech.lemnis.backgrounddataservicetester.callbacks;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import tech.lemnis.remotebackgroundservice.services.IAsyncCallback;

public class AsyncCallback implements IAsyncCallback {
    @Override
    public void handleResponse(String name) throws RemoteException {
        Log.i("Info","handleResponse : "+ name);
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}
