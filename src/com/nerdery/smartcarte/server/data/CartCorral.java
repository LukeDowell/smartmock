package com.nerdery.smartcarte.server.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldowell on 12/14/15.
 */
public class CartCorral {

    /** A grid representation of a cart corral */
    public static final int NUM_COLUMNS = 2;
    public static final int NUM_BOXES = 10;

    /** Whether the gate/box/cart is checked out (closed) or not (open) */
    public static final int CLOSED = 0x00;
    public static final int OPEN = 0x01;

    /** Representation of a cart corral */
    private static int[][] corral = new int[NUM_COLUMNS][NUM_BOXES];

    //forName in ServerMain
    static {
        for(int i = 0; i < NUM_COLUMNS; i++) {
            for(int j = 0; j < NUM_BOXES; j++) {

                // Initialize all of our values
                // Occasionally set a cart to closed
                if(Math.random() < .15) {
                    corral[i][j] = CLOSED;
                } else {
                    corral[i][j] = OPEN;
                }
            }
        }
        System.out.println("CartCorral Initialized");
    }
}
