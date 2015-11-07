package siaimaging.paysol;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import siaimaging.paysol.utils.DataStorage;

/**
 * Created by dna on 11/6/15.
 */
public class PaySolApplication extends Application {
    private static Context sContext;
    private String className = this.getClass().getName();

    @Override
    public void onCreate() {
        Log.i(className, "PaySolApplication onCreate()");
        super.onCreate();
        PaySolApplication.sContext = getApplicationContext();
        DataStorage.getInstance().setContext(sContext);
    }
}
