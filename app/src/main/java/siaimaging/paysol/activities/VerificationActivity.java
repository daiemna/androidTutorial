package siaimaging.paysol.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.logging.Handler;

import siaimaging.paysol.R;

public class VerificationActivity extends AppCompatActivity{
    private final String className = this.getClass().getSimpleName();

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mProgress = (ProgressBar) findViewById(R.id.verification_progress_bar);

        final android.os.Handler handler = new android.os.Handler();

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(className, "Write the face verification routines here.");

                            finish();
                        }
                    }, 10000);

                    mProgressStatus = mProgressStatus + 10;
                    mProgress.setProgress(mProgressStatus);
                }
            }
        }).start();
    }
}
