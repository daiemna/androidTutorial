package siaimaging.paysol.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import siaimaging.paysol.R;
import siaimaging.paysol.utils.DataStorage;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FaceCaptureActivity extends AppCompatActivity {
    private VideoView mVideoView;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private final String className = this.getClass().getSimpleName();

    private Uri mVideoUri;
    private boolean mVideoURISet;

    public Uri getVideoURI() {
        return mVideoUri;
    }

    public boolean isVideoURISet(){
        return mVideoURISet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoURISet = false;
        setContentView(R.layout.activity_face_capture);
        mVideoView = (VideoView)findViewById(R.id.video_view);
        Button saveButton = (Button)findViewById(R.id.face_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
    }

    private void onClickSave(){
        DataStorage storage = DataStorage.getInstance();
        try{
            Log.i(className, "creating Uri file!");
            InputStream uriInputStream = getContentResolver().openInputStream(mVideoUri);
            Log.i(className, "getting FaceVideoStorageFile");
            OutputStream videoFile = storage.createPrivateFile(DataStorage.FaceVideoStorageFile);
            int fileSize = uriInputStream.available();
            Log.i(className, "video File size : " + fileSize);
            byte[] data = new byte[fileSize];
            if(uriInputStream.read(data,0,fileSize)> 0){

            }


            Log.i(className, "Data copied!");
        }catch (Exception e){
            Log.e(className, "Exception : " + e.toString());
            finish();
            return;
        }

        Intent registerUserIntent = new Intent(this, RegisterUserActivity.class);
        startActivity(registerUserIntent);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dispatchTakeVideoIntent();
    }

    private void dispatchTakeVideoIntent() {
        Log.i(className, "dispatchTakeVideoIntent() called");
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            Log.i(className, "Starting activity for result!");
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(className, "onActivityResult(" + requestCode + "," + resultCode + "," + intent.getAction() + ")");
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Log.i(className, "Populating video view where RESULT_OK : " + RESULT_OK);
            mVideoUri = intent.getData();
            Log.i(className, "URI : " + mVideoUri.getEncodedPath());
            mVideoView.setVideoURI(mVideoUri);
            mVideoURISet = true;
        }else{
            Log.i(className, "Closing the FaceCaptureActivity");
            mVideoURISet = false;
            this.finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mVideoView.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.i(className, "launching LoginActivity after " + splash_delay_ms + "ms");
                pauseVideo();
            }
        }, 500);
    }

    private void pauseVideo(){
        mVideoView.pause();
    }
}
