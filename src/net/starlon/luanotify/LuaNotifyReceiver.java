package net.starlon.luanotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LuaNotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, LuaNotifyService.class);
        context.startService(service);
    }
}
