package tw.org.iii.appps.brad26;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private MyService myService;
    private boolean isBind;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder)iBinder;
            myService = binder.getService();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBind){
            unbindService(mConnection);
        }
    }

    public void test1(View view) {
        if (isBind && myService != null){
            int lottery = myService.createLottery();
            tv.setText("Lottery:" + lottery);
        }
    }
}
