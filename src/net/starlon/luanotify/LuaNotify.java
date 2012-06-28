package net.starlon.luanotify;

import android.app.ActivityManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.content.Context;
import android.content.ContentUris;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ViewConfiguration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.media.AudioFormat;
import android.util.Log;
import android.net.Uri;
import android.database.Cursor;
import android.provider.MediaStore;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileDescriptor;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.lang.Process;
import java.lang.CharSequence;

import net.starlon.libscriptable.UtilsEvaluator;

public class LuaNotify extends Activity implements OnClickListener, OnSharedPreferenceChangeListener
{
    private final static String TAG = "LuaNotify/LuaNotifyActivity";
    private final static String PREFS = "LuaNotifyPrefs";

    private UtilsEvaluator mEvaluator;

    public final Object mSynch = new Object();

    public UtilsEvaluator getEvaluator()
    {
        return mEvaluator;
    }

    private void makeFile(String file, int id)
    {
        InputStream inputStream = getResources().openRawResource(id);
     
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
     
        int i;
        try {
            i = inputStream.read();
            while (i != -1)
            {
                outputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        //makeFile("/data/data/com.starlon.starvisuals/libstub.lua", R.raw.libstub);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mEvaluator = new UtilsEvaluator();

/*
        String val = mEvaluator.eval("return LCD.uptime('%d d %H:%M:%S')");
        Log.w(TAG, "HAH " + val);
*/

        //mPrefs = getSharedPreferences(PREFS, 0);
        //mPrefs.registerOnSharedPreferenceChangeListener(this);

        //mEditor = mPrefs.edit();

    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) 
    {

        if(key.equals("prefs_actor_selection"))
        {
            //mActor = mPrefs.getString(key, ACTOR);
            //NativeHelper.actorSetCurrentByName(mActor, true);

        }
    }

    public void onClick(View v)
    {

    }

    // This series of on<Action>() methods a flow chart are outlined here:
    // http://developer.android.com/reference/android/app/Activity.html

    // User returns to activity
    @Override
    public void onResume() 
    {

        super.onResume();

    }

    // follows onCreate() and onResume()
    @Override
    protected void onStart() 
    {   
        super.onStart();

    }

    // another activity comes to foreground
    @Override
    protected void onPause() 
    {
        super.onPause();

    }

    // user navigates back to the activity. onRestart() -> onStart() -> onResume()
    @Override
    protected void onRestart() 
    {
        super.onRestart();
    }

    // This activity is no longer visible
    @Override
    protected void onStop()
    {
        super.onStop();

    }

    // Last method before shut down. Clean up LibVisual from here.
    @Override 
    protected void onDestroy()
    {
        super.onDestroy();
    }

    // Create options menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.starvisuals, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
/*
            case R.id.menu_about:
            {
                //startActivity(new Intent(this, AboutActivity.class));
                return true;
            }
*/
            default:
            {
                Log.w(TAG, "Unhandled menu-item. This is a bug!");
                break;
            }
        }
        return false;
    }
    /* load our native library */
    static {
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("luascript");
        System.loadLibrary("scriptable");
    }

/*
    public String _S(int id)
    {
        return getResources().getText(id).toString();
    }
    public int _I(int id)
    {
        return getResources().getInteger(id);
    }
    public boolean _B(int id)
    {
        String bool = getResources().getString(id).toString();
        if(bool.equals("true"))
            return true;
        return false;
    }
*/
}


