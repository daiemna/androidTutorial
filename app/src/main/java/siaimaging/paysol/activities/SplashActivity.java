package siaimaging.paysol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import siaimaging.paysol.R;

public class SplashActivity extends Activity {

    private ImageView mSplash;
//    private
    private final String className = this.getClass().getSimpleName();
    private int splash_delay_ms = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(className, "onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplash = (ImageView)findViewById(R.id.splash_image);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.i(className, "onPostCreate() called");
        super.onPostCreate(savedInstanceState);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(className, "launching LoginActivity after " + splash_delay_ms + "ms");
                //launching Login Activity after 2 seconds
                launchLoginActivity();
            }
        }, 5000);
    }

    private void launchLoginActivity(){
        Log.i(className, "launchLoginActivity() called");
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
