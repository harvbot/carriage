package com.harvbot.server;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Oleg on 3/19/2017.
 */
public class HarvbotVideoServer extends NanoHTTPD
{
    private static final String TAG = "JavaCameraView";

    public static final int CAMERA_ID_ANY =	-1;
    public static final int CAMERA_ID_BACK = 99;
    public static final int CAMERA_ID_FRONT = 98;

    private Camera mCamera;

    //private int mWidth;

    //private int mHeight;

    private YuvImage lastYuvImage;

    private SurfaceView mCameraPreview;

    private SurfaceHolder previewHolder;

    private boolean inPreview;

    private Size mPreviewSize;

    public HarvbotVideoServer(SurfaceView surfacePreview, int port)
    {
        super(port);

        this.mCameraPreview = surfacePreview;

        previewHolder = mCameraPreview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        int mCameraIndex = this.findFrontFacingCamera();

        mCamera = Camera.open(mCameraIndex);

        mPreviewSize = mCamera.getParameters().getPreviewSize();
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        return new Response(Response.Status.OK, "image/jpeg",
                new ByteArrayInputStream(getByteArray()));
    }

    private int findFrontFacingCamera() {

        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                return i;
            }
        }

        return -1;
    }

    /*
	 * SurfaceHolder callback triple
	 */
    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        public void surfaceCreated(SurfaceHolder holder) {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

            //Camera.Parameters parameters = mCamera.getParameters();
            //parameters.setPreviewSize(mWidth, mHeight);

            mCamera.setPreviewCallback(cameraPreviewCallback);
            mCamera.startPreview();

            inPreview = true;
        }

        /*
         * Destroy State: Take care on release of camera
         *
         * @see
         * android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
         * SurfaceHolder)
         */
        public void surfaceDestroyed(SurfaceHolder holder) {

            if (inPreview) {
                mCamera.stopPreview();
            }
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;

            inPreview = false;

            // signal to stop active MJPG streams
            lastYuvImage = null;
        }

        private int findFrontFacingCamera() {

            // Search for the front facing camera
            int numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                    return i;
                }
            }

            return -1;
        }
    };

    private byte[] getByteArray() {

        if (lastYuvImage == null) {
            return new byte[0];
        }
        else {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            Rect rect = new Rect(0, 0, mPreviewSize.width, mPreviewSize.height);
            lastYuvImage.compressToJpeg(rect, 90, stream);

            byte[] bytes = null;

            try {
                bytes = stream.toByteArray();
                stream.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

            return bytes;

        }
    }

    Camera.PreviewCallback cameraPreviewCallback = new Camera.PreviewCallback() {

        public void onPreviewFrame(byte[] data, Camera camera) {
            lastYuvImage = new YuvImage(data, ImageFormat.NV21, mPreviewSize.width, mPreviewSize.height, null);
        }

    };
}

