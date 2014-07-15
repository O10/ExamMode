
package exammode.exammodev1;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 5 lip 2014 11:13:32
 */
public class EmodeAppWidgetProvider extends AppWidgetProvider {
    static boolean on = false;

    public EmodeAppWidgetProvider() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        SharedPreferences settings = context.getSharedPreferences("EModePreferences", 0);

        for (int i = 0; i < N; i++) {
            Log.i("EMODE", "Entering on Update");
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent("Action.Request.Exam.Mode");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            RemoteViews curView = new RemoteViews(context.getPackageName(),
                    R.layout.emode_widget_layoutv2);
            curView.setOnClickPendingIntent(R.id.emode_main_button, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, curView);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
