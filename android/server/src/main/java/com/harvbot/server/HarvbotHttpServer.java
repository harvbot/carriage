package com.harvbot.server;

import android.content.res.Resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class HarvbotHttpServer extends NanoHTTPD {

    private Resources mResources;

    private String mPackageName;

    private ArrayList<IHarvbotServerEventListener> commandListeners;

    public HarvbotHttpServer(Resources resources, String packageName, int port) throws IOException
    {
        super(port);

        this.mResources = resources;
        this.mPackageName = packageName;
        this.commandListeners = new ArrayList<IHarvbotServerEventListener>();
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        String url = session.getUri().toLowerCase();
        if(url.startsWith("/api"))
        {
            String[] segments = url.split("/");
            this.onServerCommand(segments[2].trim(), segments[3].trim());
            return new Response(Response.Status.OK, "application/json", "{\"success\":true}");
        }
        else
        {
            if(!url.equalsIgnoreCase("/"))
            {
                String mimeType = this.getResponseMimeType(url);
                String filename = url.substring(1, url.lastIndexOf('.'));

                int resourceId = this.mResources.getIdentifier(filename, "raw", this.mPackageName);
                return new Response(Response.Status.OK, mimeType, this.mResources.openRawResource(resourceId));
            }
            else
            {
                return new Response(Response.Status.OK, NanoHTTPD.MIME_HTML, this.mResources.openRawResource(R.raw.index));
            }
        }
    }

    public void setServerEventListener(IHarvbotServerEventListener listener)
    {
        this.commandListeners.add(listener);
    }

    private void onServerCommand(String api, String command)
    {
        for(int i=0;i<this.commandListeners.size();i++)
        {
            this.commandListeners.get(i).onCommand(api, command);
        }
    }

    private String getResponseMimeType(String filename)
    {
        if(filename.endsWith(".js"))
        {
            return "text/javascript";
        }
        else
        {
            return NanoHTTPD.MIME_HTML;
        }
    }
}
