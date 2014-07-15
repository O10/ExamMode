
package exammode.exammodev1;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 2 lip 2014 13:58:55
 */
public class EMRequestReceiver extends BroadcastReceiver {

    static final String PREFS_NAME = "EModePreferences";

    public EMRequestReceiver() {
    }

    private void examModeOn(Context context) {

        Log.i("EMODE", "Entering examModeOn");
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        SharedPreferences.Editor editor = settings.edit();

        if (settings.getBoolean("disableSound", false))
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        if (settings.getBoolean("extTimeout", false)) {
            int prevTimeout = 60; // time in seconds

            try {
                prevTimeout = android.provider.Settings.System.getInt(context.getContentResolver(),
                        android.provider.Settings.System.SCREEN_OFF_TIMEOUT) / 1000;
            } catch (SettingNotFoundException e) {
                e.printStackTrace();
            }

            if (!settings.getBoolean("isOn", false)) {
                Log.i("EMODE", "Entering ison");
                editor.putInt("originalTimeout", prevTimeout);
            }

            int newTimeout = Integer.parseInt(settings.getString("timeout", "1")) * 60;

            android.provider.Settings.System.putInt(context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_OFF_TIMEOUT, newTimeout * 1000);

            editor.putBoolean("isOn", true);

            editor.commit();

            Log.i("EMODE", Integer.toString(newTimeout));

        }

    }

    private void examModeOff(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Log.i("EMODE", "Entering examModeOff");
        if (!settings.getBoolean("isOn", false))
            return;

        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        int originalTimeout = settings.getInt("originalTimeout", 60);
        android.provider.Settings.System.putInt(context.getContentResolver(),
                android.provider.Settings.System.SCREEN_OFF_TIMEOUT, originalTimeout * 1000);

        Log.i("EMODE", "Original timeout" + Integer.toString(originalTimeout));
        editor.putBoolean("isOn", false);
        editor.commit();

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.emode_widget_layoutv2);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if (intent.hasExtra("start")) {
            if (intent.getBooleanExtra("start", true))
                examModeOn(context);
            else
                examModeOff(context);
        } else {
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            if (settings.getBoolean("isOn", false)) {
                examModeOff(context);
                view.setTextViewText(R.id.emode_v2_text, "Off");
                appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(),
                        EmodeAppWidgetProvider.class.getName()), view);

            } else {
                examModeOn(context);
                view.setTextViewText(R.id.emode_v2_text, "On");
                appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(),
                        EmodeAppWidgetProvider.class.getName()), view);
            }
        }

    }
}
