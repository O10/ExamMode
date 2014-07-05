
package exammode.exammodev1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 2 lip 2014 13:58:55
 */
public class EMRequestReceiver extends BroadcastReceiver {

    static final String PREFS_NAME = "EModePreferences";

    static boolean isOn = false;

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

            if (!isOn) {
                Log.i("EMODE", "Entering ison");
                editor.putInt("originalTimeout", prevTimeout);
            }

            int newTimeout = Integer.parseInt(settings.getString("timeout", "1")) * 60;

            android.provider.Settings.System.putInt(context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_OFF_TIMEOUT, newTimeout * 1000);

            isOn = true;

            editor.commit();

            Log.i("EMODE", Integer.toString(newTimeout));

        }

    }

    private void examModeOff(Context context) {
        Log.i("EMODE", "Entering examModeOff");
        if (!isOn)
            return;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        int originalTimeout = settings.getInt("originalTimeout", 60);
        android.provider.Settings.System.putInt(context.getContentResolver(),
                android.provider.Settings.System.SCREEN_OFF_TIMEOUT, originalTimeout * 1000);

        isOn = false;
        Log.i("EMODE", "Original timeout" + Integer.toString(originalTimeout));

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getBooleanExtra("start", true))
            examModeOn(context);
        else
            examModeOff(context);

    }
}
