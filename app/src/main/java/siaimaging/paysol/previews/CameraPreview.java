package siaimaging.paysol.previews;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

/**
 * Created by dna on 10/23/15.
 */
public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {
    SurfaceView mSurfaceView;
    SurfaceHolder mHolder;
    private Camera mCamera;
    private List<Camera.Size> mSupportedPreviewSizes;
    private final String className = this.getClass().getSimpleName();

    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Log.i(className, "init() called!");
//        mSurfaceView = (SurfaceView)findViewById(R.id.surface_view);
        mSurfaceView = new SurfaceView(context);
        addView(mSurfaceView);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Log.i(className, "EXITING init()");

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(className, "onLayout(" + changed + " , " + l + " , " + t + " , " + r + " , " + b + ")");

        mSurfaceView.setRight(r);
        mSurfaceView.setTop(t);
        mSurfaceView.setLeft(l);
        mSurfaceView.setBottom(b);

        Log.i(className, "mSurfaceView  width,height : (" + mSurfaceView.getWidth() + ", " + mSurfaceView.getHeight() + ")");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(className, "surfaceCreated() called!");
        // Now that the size is known, set up the camera_preview parameters and begin
        // the preview.
        setPreviewHolder(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(className, "surfaceChanged()");
        mCamera.stopPreview();
        setPreviewParams(width);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(className, "surfaceDestroyed()");
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();
        }
    }

    public void setCamera(Camera camera) {
        if (mCamera == camera) {
            return;
        }

        stopPreviewAndFreeCamera();

        mCamera = camera;

        if (mCamera != null) {
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
            requestLayout();

        }
    }

    private void stopPreviewAndFreeCamera() {

        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();

            // Important: Call release() to release the camera_preview for use by other
            // applications. Applications should release the camera_preview immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();

            mCamera = null;
        }
    }

    private void setPreviewParams(int nearWidth) {
        Camera.Parameters parameters = mCamera.getParameters();
        Log.i(className, "getting mCamera parameters");
        Log.i(className, "mSupportedPreviewSizes : " + mSupportedPreviewSizes.size());
        int minDiff = 999999;
        int previewSizeIndex = mSupportedPreviewSizes.size() - 1;

        for (int i = 0; i < mSupportedPreviewSizes.size(); i++) {
            Camera.Size size = mSupportedPreviewSizes.get(i);
            Log.i(className, "Supported size (width,height) : (" + size.width + "," + size.height + ")");
            if ((nearWidth - size.width) > 0 && Math.abs(nearWidth - size.width) < minDiff) {
                previewSizeIndex = i;
                Log.i(className, "Selecte Priview index : " + i);
                minDiff = Math.abs(nearWidth - size.width);
            }
        }
        Log.i(className, "Selected Preview Size (" + mSupportedPreviewSizes.get(previewSizeIndex).width + "," + mSupportedPreviewSizes.get(previewSizeIndex).height + ")");
        parameters.setPreviewSize(mSupportedPreviewSizes.get(previewSizeIndex).width,
                mSupportedPreviewSizes.get(previewSizeIndex).height);

        Log.i(className, "parameters.setPreviewParams = (" + parameters.getPreviewSize().width + "," + parameters.getPreviewSize().height + ")");
        requestLayout();
        Log.i(className, "returned from requestLayout()");
        mCamera.setParameters(parameters);
        Log.i(className, "parameters set to mCamera");
    }

    private void setPreviewHolder(SurfaceHolder holder) {
        Log.i(className, "setPreviewHolder() called");
        try {
            mCamera.setPreviewDisplay(holder);
            Log.i(className, "setPreviewDisplay is passed mHolder");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
