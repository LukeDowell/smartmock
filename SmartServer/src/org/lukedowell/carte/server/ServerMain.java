package org.lukedowell.carte.server;

import org.lukedowell.carte.server.data.ChaosCart;
import org.lukedowell.carte.server.data.CartCorral;

import java.io.IOException;

/**
 * Created by ldowell on 12/16/15.
 */
public class ServerMain {

    public static void main(String[] args) {
        try {
            // Initialize our data
            CartCorral.initialize();

            // Initialize the chaos monkey
            new Thread(new ChaosCart()).start();

            // Initiailize our server
            new CarteServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
