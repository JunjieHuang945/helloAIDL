package top.foxhome.demo.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class AIDLServer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new FoxBinder();
    }

    class FoxBinder extends IFoxAidlInterface.Stub {

        @Override
        public String say(String text) throws RemoteException {
            Log.e("fxlog", "text:" + text);
            return "fox author:hi,client.you say[" + text + "]";
        }
    }
}
