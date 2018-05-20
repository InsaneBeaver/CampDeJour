package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Map;


public class CampDeJour extends Application {
    public static InterfaceClient interfaceClient;

    public static void afficherMessage(Context contexte, String message, String titre, String boutonPositif)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(contexte);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle(titre);
        dlgAlert.setPositiveButton(boutonPositif, null);
        dlgAlert.create().show();
    }

    /// https://stackoverflow.com/a/28423385
    public static Activity getActivity() throws Exception {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);

        Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
        if (activities == null)
            return null;

        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                return activity;
            }
        }

        return null;
    }

    public static void afficherToast(final String message)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                try {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
                catch(Exception e) {}
            }
        });

    }

    public static void execAsync(final Runnable runnable)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                runnable.run();
            } });
    }


}
