package com.harvbot.carriage.app;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.XmlResourceParser;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.harvbot.carriage.HarvbotCarriage;
import com.harvbot.server.HarvbotHttpServer;
import com.harvbot.server.HarvbotVideoServer;
import com.harvbot.server.IHarvbotServerEventListener;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UsbManager usbManager;

    private UsbDevice botDevice;

    private static final String ACTION_USB_PERMISSION = "com.harvbot.myapplication";

    private HarvbotCarriage carriage;

    private HarvbotHttpServer server;

    private HarvbotVideoServer videoServer;

    private SurfaceView mCameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.harvbot.carriage.app.R.layout.activity_main);

        this.mCameraPreview = (SurfaceView) findViewById(com.harvbot.carriage.app.R.id.smallcameraview);

        this.usbManager = (UsbManager)this.getSystemService(Context.USB_SERVICE);

        this.findControlDevice();

        this.startServer();


    }

    private void startServer()
    {
        try {
            this.server = new HarvbotHttpServer(this.getResources(), this.getPackageName(), 8080);
            this.server.setServerEventListener(new IHarvbotServerEventListener()
            {
                @Override
                public void onCommand(String api, String command)
                {
                    if(api.equalsIgnoreCase("carriage"))
                    {
                        try {
                            carriage.sendCommand(command);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            this.server.start();

            // Start video server.
            //this.videoServer = new HarvbotVideoServer(mCameraPreview, 8081);
            //this.videoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findControlDevice() {

        int productId = 0;
        int vendorId = 0;
        int eventType = -1;
        XmlResourceParser document = getResources().getXml(com.harvbot.carriage.app.R.xml.device_filter);

        while(eventType != XmlResourceParser.END_DOCUMENT)
        {
            String name = document.getText();

            try {
                if (document.getEventType() == XmlResourceParser.START_TAG) {
                    String s = document.getName();

                    if (s.equals("usb-device")) {
                        vendorId = Integer.parseInt(document.getAttributeValue(null, "vendor-id"));
                        productId = Integer.parseInt(document.getAttributeValue(null, "product-id"));
                        break;
                    }
                }

                eventType = document.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Iterator<UsbDevice> deviceIterator = usbManager.getDeviceList().values().iterator();

        while (deviceIterator.hasNext())
        {
            UsbDevice device = deviceIterator.next();

            // Find speicified divce.
            if(device.getVendorId()== vendorId && device.getProductId()==productId)
            {
                this.botDevice = device;
                break;
            }
        }

        if(this.botDevice != null) {
            // Request usb device permission.
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            this.registerReceiver(mUsbReceiver, filter);

            usbManager.requestPermission(this.botDevice, mPermissionIntent);
        }
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, true)) {
                        if(device != null)
                        {
                            try
                            {
                                // Create Harvbot Carriage instance.
                                carriage = new HarvbotCarriage(usbManager, device);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Log.d("TAG", "permission denied for the device " + device);
                    }
                }
            }
        }
    };
}
