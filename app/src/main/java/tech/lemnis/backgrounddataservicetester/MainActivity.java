package tech.lemnis.backgrounddataservicetester;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;

import tech.lemnis.backgrounddataservicetester.asynctasks.DataServiceInvokerAsyncTask;
import tech.lemnis.backgrounddataservicetester.services.TurnAroundTimeService;
import tech.lemnis.remotebackgroundservice.services.IRemoteService;

public class MainActivity extends AppCompatActivity {

    private IRemoteService remoteService;
    private TurnAroundTimeService turnAroundTimeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Info","Binding DataService");
        Intent dataServiceIntent = new Intent("tech.lemnis.remotebackgroundservice.services.IRemoteService");
        dataServiceIntent.setPackage("tech.lemnis.remotebackgroundservice");
        bindService(dataServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        JodaTimeAndroid.init(this);
        // use Dependency Injection to bind Services and inject whenever needed.
        turnAroundTimeService = new TurnAroundTimeService();
    }
    public void magic(View view) {
        clearViews();
        String largeString = createLargeString("Hello World");
        // The params contains 3 values: string to be sent, the view to be updated, delay in milliseconds.
        String[] params1 = {largeString, Integer.toString(R.id.asyncResultView1), "5300"};
        String[] params2 = {largeString, Integer.toString(R.id.asyncResultView2), "1300"};
        String[] params3 = {largeString, Integer.toString(R.id.asyncResultView3), "2400"};
        String[] params4 = {largeString, Integer.toString(R.id.asyncResultView4), "400"};

     // ExecuteOnExecutor executes tasks on different threads from available threadpool, hence parallel execution.
        turnAroundTimeService.recordStart(R.id.asyncResultView1);
        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
        new DataServiceInvokerAsyncTask(remoteService, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params1);
        turnAroundTimeService.recordStart(R.id.asyncResultView2);
        findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
        new DataServiceInvokerAsyncTask(remoteService, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params2);
        turnAroundTimeService.recordStart(R.id.asyncResultView3);
        findViewById(R.id.progressBar3).setVisibility(View.VISIBLE);
        new DataServiceInvokerAsyncTask(remoteService, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params3);
        turnAroundTimeService.recordStart(R.id.asyncResultView4);
        findViewById(R.id.progressBar4).setVisibility(View.VISIBLE);
        new DataServiceInvokerAsyncTask(remoteService, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params4);
    }

    public void updateViews(double size, int viewToUpdate) {
        TextView textView = findViewById(viewToUpdate);
        turnAroundTimeService.recordStop(viewToUpdate);
        Duration duration = turnAroundTimeService.getDuration(viewToUpdate);
        String durationString = PeriodFormat.getDefault().print(duration.toPeriod());
        switch (viewToUpdate){
            case R.id.asyncResultView1:
                findViewById(R.id.progressBar1).setVisibility(View.INVISIBLE);
                break;
            case R.id.asyncResultView2:
                findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                break;
            case R.id.asyncResultView3:
                findViewById(R.id.progressBar3).setVisibility(View.INVISIBLE);
                break;
            case R.id.asyncResultView4:
                findViewById(R.id.progressBar4).setVisibility(View.INVISIBLE);
                break;
        }
        textView.setText(String.format("Size of the Data : %s KB, turn around time : %s " , size, durationString));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
        Log.i("Info", "unbinding remoteService...");
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("Info",String.format("ComponentName %s service %s", name, service));
            remoteService = IRemoteService.Stub.asInterface(service);
            Log.i("Info",String.format("remoteService.getClass().getName() %s", remoteService.getClass().getName()));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService = null;
            Log.e("Error", "Service has unexpectedly disconnected");
        }
    };


    private String createLargeString(String seedString) {
        String largeString = seedString;
        for(int i=0;i < 13; i++) {
            largeString = largeString.concat(largeString);
        }
        return largeString;
    }

    private void clearViews() {
        ((TextView) findViewById(R.id.asyncResultView1)).setText("");
        ((TextView) findViewById(R.id.asyncResultView2)).setText("");
        ((TextView) findViewById(R.id.asyncResultView3)).setText("");
        ((TextView) findViewById(R.id.asyncResultView4)).setText("");
    }
}
