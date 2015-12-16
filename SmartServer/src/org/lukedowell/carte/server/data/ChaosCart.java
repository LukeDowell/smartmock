package org.lukedowell.carte.server.data;

import java.awt.*;

/**
 * Created by ldowell on 12/16/15.
 */
public class ChaosCart implements Runnable {

    public static final int SLEEP_TIME = 3000; // 3 seconds

    @Override
    public void run() {
        System.out.println("Starting chaos cart...");

        while(!Thread.currentThread().isInterrupted()) {

            if(Math.random() < .5) {
                // 50% chance to do something

                // Take out a random cart
                Point firstCart = CartCorral.findFirstCart();
                if(firstCart != null) {
                    CartCorral.setHasCart(firstCart.x, firstCart.y, false);
                }

                Point firstEmptyBox = CartCorral.findFirstEmptyBox();
                if(firstEmptyBox != null) {
                    CartCorral.setHasCart(firstEmptyBox.x, firstEmptyBox.y, true);
                }
                System.out.println("Chaos Cart: mischief complete");
            }

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
