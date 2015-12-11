package com.nerdery.smartcarte;

import com.nerdery.smartcarte.client.SmartClient;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by ldowell on 12/11/15.
 */
public class ClientMain {

    public static void main(String[] args) {
        try {
            new SmartClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
