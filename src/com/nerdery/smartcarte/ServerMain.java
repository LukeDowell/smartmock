package com.nerdery.smartcarte;

import com.nerdery.smartcarte.server.SmartServer;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by ldowell on 12/11/15.
 */
public class ServerMain {

    public static void main(String[] args) {
        try {
            Class.forName("com.nerdery.smartcarte.server.data.CartCorral");
            new SmartServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
