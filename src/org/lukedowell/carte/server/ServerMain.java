package org.lukedowell.carte.server;

import org.lukedowell.carte.server.data.CartCorral;
import org.lukedowell.carte.server.data.ChaosCart;

/**
 * Created by ldowell on 12/16/15.
 */
public class ServerMain {

    public static void main(String[] args) {
        try {

            // Spin up our data
            new CartCorral();

            new Thread(new ChaosCart()).start();

            // Initiailize our server
            new CarteServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
