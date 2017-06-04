package com.harvbot.server;

/**
 * Created by Oleg on 3/19/2017.
 */
public interface IHarvbotServerEventListener {

    public void onCommand(String api, String command);
}
