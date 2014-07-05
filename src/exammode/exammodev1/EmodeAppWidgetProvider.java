
package exammode.exammodev1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 5 lip 2014 11:13:32
 */
public class EmodeAppWidgetProvider extends AppWidgetProvider {

    public EmodeAppWidgetProvider() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        /*
         * for (int i = 0; i < N; i++) { Log.i("EMODE", "Entering on Update");
         * int appWidgetId = appWidgetIds[i]; Intent intent = new
         * Intent(context, ExamModeActivity.class); PendingIntent pendingIntent
         * = PendingIntent.getActivity(context, 0, intent, 0); RemoteViews
         * curView = new RemoteViews(context.getPackageName(),
         * R.layout.emode_widget_layout);
         * curView.setOnClickPendingIntent(R.id.widget_image_view,
         * pendingIntent); appWidgetManager.updateAppWidget(appWidgetId,
         * curView); }
         */
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
