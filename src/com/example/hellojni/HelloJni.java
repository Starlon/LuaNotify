package net.starlon.luanotify;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;


public class LuaNotify extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    static {
        System.loadLibrary("luanotify");
    }
}
