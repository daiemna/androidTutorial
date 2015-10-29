package siaimaging.paysol.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import siaimaging.paysol.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FaceCaptureActivity extends AppCompatActivity {
    private VideoView mVideoView;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private final String className = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_face_capture);
        mVideoView = (VideoView)findViewById(R.id.video_view);
//        mVideoView.
    }
    private void dispatchTakeVideoIntent() {
        Log.i(className, "dispatchTakeVideoIntent() called");
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(className, "onActivityResult("+requestCode+","+resultCode+","+intent.getAction()+")");
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Log.i(className, "Populating video view where RESULT_OK : " + RESULT_OK);
            Uri videoUri = intent.getData();
            Log.i(className, "URI : " + videoUri.getEncodedPath());
            mVideoView.setVideoURI(videoUri);
        }else{
            Log.i(className, "Closing the FaceCaptureActivity");
            this.finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dispatchTakeVideoIntent();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mVideoView.start();
//        mVideoView.pause();
    }
}
