package com.harvbot.carriage;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.util.Log;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.Ch34xSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;

public class HarvbotCarriage {

    public static final int BaudRate = 9600;

    public static final int Timeout = 1000;

    private UsbSerialPort serialPort;

    public HarvbotCarriage(UsbManager usbManager, UsbDevice device) throws IOException {

        UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(device);

        UsbSerialDriver serial = UsbSerialProber.getDefaultProber().probeDevice(device);

        if(serial == null)
        {
            throw new UnsupportedOperationException("Specified device is not supported");
        }

        if( serial.getPorts().size() == 0)
        {
            throw new UnsupportedOperationException("Specified driver doesn't have any serial ports");
        }

        this.serialPort = serial.getPorts().get(0);
        this.serialPort.open(usbDeviceConnection);
        this.serialPort.setDTR(true);
        this.serialPort.setRTS(true);
    }

    public HarvbotCarriage(UsbSerialPort port) throws IOException {
        this.serialPort = port;
    }

    public UsbSerialPort getSerialPort() {
        return this.serialPort;
    }

    public void open() throws IOException
    {

    }

    public void close() throws IOException
    {
        this.serialPort.close();
    }

    public void forward() throws IOException {
        this.sendCommand("forward");
    }

    public void backward() throws IOException {
        this.sendCommand("forward");
    }

    public void right() throws IOException {
        this.sendCommand("right");
    }

    public void left() throws IOException {
        this.sendCommand("left");
    }

    public int getSpeed() throws IOException {
        String result = this.sendCommand("get-speed");

        return Integer.getInteger(result);
    }

    public void setSpeed(int speed) throws IOException {
        this.sendCommand("set-speed", (new Integer(speed)).toString());
    }

    public String sendCommand(String command) throws IOException
    {
        return this.sendCommand(command, null);
    }

    public String sendCommand(String command, String args) throws IOException
    {
        UsbSerialPort serailPost = this.getSerialPort();
        StringBuilder request = new StringBuilder();

        request.append("hcarriage:" + command);
        if (!TextUtils.isEmpty(args))
        {
            request.append( ":" + args);
        }

        request.append(":~hcarriage\n");

        Log.v("CARRIAGE", "Request: " + request.toString());

        byte[] requestBytes = request.toString().getBytes("ASCII");
        serailPost.write(requestBytes, Timeout);

        byte[] responseBytes = new byte[128];

        StringBuilder response = new StringBuilder();

        String responsePart = "";
        do {
            int read = serailPost.read(responseBytes, Timeout);
            responsePart = (new String(responseBytes, "ASCII")).trim();
            response.append(responsePart + " ");
        }
        while(!responsePart.equalsIgnoreCase(request.toString().trim()));

        Log.v("CARRIAGE", "Response: " + response.toString());

        if (!TextUtils.isEmpty(response))
        {
            String[] segments = response.toString().split(":");

            if (segments[0].equalsIgnoreCase("hcarriage") &&
                    segments[1].equalsIgnoreCase(command) &&
                    segments[segments.length - 1].equalsIgnoreCase("~hcarriage")) {
                return segments[segments.length - 2];
            }
        }

        return "";
    }
}
