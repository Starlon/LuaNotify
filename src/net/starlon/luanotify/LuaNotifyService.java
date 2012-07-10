package net.starlon.luanotify;

import android.app.Service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Binder;
import java.lang.CharSequence;

import net.starlon.libscriptable.UtilsEvaluator;

import java.util.TimerTask;
import java.util.Timer;

public class LuaNotifyService extends Service {
    private NotificationManager mNM;
    final private int NOTIFICATION = 0xdead;
    final private String PREFS = "lua_notify_prefs";
    private UtilsEvaluator mEvaluator;
    private Timer mTimer;
    private TimerTask mTask;
    private SharedPreferences mPrefs;
    private String mScript;
    //private Context mContext;

    public class LocalBinder extends Binder {
        LuaNotifyService getService() {
            return LuaNotifyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //mContext = getApplicationContext();
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        System.loadLibrary("gnustl_shared");
        System.loadLibrary("luascript");
        System.loadLibrary("scriptable");

        mEvaluator = new UtilsEvaluator();

        mPrefs = getSharedPreferences(PREFS, 0);

        String def = getResources().getString(R.string.prefs_script).toString();
        mScript = mPrefs.getString("prefs_script", def);

        mTask = new TimerTask(){public void run() { 
            String txt = mEvaluator.eval(mScript); 
            showNotification(txt);
        }}; 

        mTimer = new Timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimer.scheduleAtFixedRate(mTask, 0, 300);
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
