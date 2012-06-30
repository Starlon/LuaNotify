package net.starlon.luanotify;

import android.app.Service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Notification;
import android.os.IBinder;
import android.os.Binder;
import java.lang.CharSequence;

import net.starlon.libscriptable.UtilsEvaluator;

public class LuaNotifyService extends Service {
    private NotificationManager mNM;
    private int NOTIFICATION = 0xdead;
    private UtilsEvaluator mEvaluator;

    public class LocalBinder extends Binder {
        LuaNotifyService getService() {
            return LuaNotifyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        System.loadLibrary("gnustl_shared");
        System.loadLibrary("luascript");
        System.loadLibrary("scriptable");

        mEvaluator = new UtilsEvaluator();

        showNotification(mEvaluator.eval("return uname.Uname('sysname')"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mNM.cancel(NOTIFICATION);
        
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    private void showNotification(String arg)
    {
    
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = arg;

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(android.R.drawable.btn_star_big_on, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LuaNotifyActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.app_name),
                       text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
    
}
