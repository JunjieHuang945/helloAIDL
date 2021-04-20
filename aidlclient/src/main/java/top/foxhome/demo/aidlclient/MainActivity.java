package top.foxhome.demo.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import net.sunniwell.demo.aidlclient.R;

import top.foxhome.demo.aidlserver.IFoxAidlInterface;

public class MainActivity extends AppCompatActivity {
    private IFoxAidlInterface iFoxAidlInterface;
    private Button sayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 隐形匹配action
         */
        Intent mIntent = new Intent("top.foxhome.demo.AIDLServer.action");
        /**
         * 服务应用的包名
         */
        mIntent.setPackage("top.foxhome.demo.aidlserver");
        bindService(mIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //连接成功
                iFoxAidlInterface = IFoxAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //连接意外中断
            }
        }, BIND_AUTO_CREATE);
        sayBtn = findViewById(R.id.say_btn);
        sayBtn.setOnClickListener((v) -> {
            if (iFoxAidlInterface != null) {
                try {
                    String serverStr = iFoxAidlInterface.say("hello,AIDL！" + System.currentTimeMillis());
                    Log.e("fxlog", "msg form server:" + serverStr);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e("fxlog", "RemoteException:" + e);
                }
            } else {
                Log.e("fxlog", "iFoxAidlInterface==null");
            }
        });
    }
}