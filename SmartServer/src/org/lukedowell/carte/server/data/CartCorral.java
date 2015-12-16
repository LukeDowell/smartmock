package org.lukedowell.carte.server.data;

import java.awt.*;

/**
 * Created by ldowell on 12/16/15.
 */
public class CartCorral {

    /** The number of 'columns' to initialize our corral */
    public static final int NUM_COLUMNS = 2;

    /** The number of 'boxes' each column will have */
    public static final int NUM_BOXES = 10;

    /** Our corral */
    private static int[][] corral = new int[NUM_COLUMNS][NUM_BOXES];

    public static final int CHECKED_OUT = 0x00;
    public static final int CHECKED_IN = 0x01;

    /**
     * Initializes the cart corral with random values
     */
    public static void initialize() {
        for(int col = 0; col < NUM_COLUMNS; col++) {
            for(int box = 0; box < NUM_BOXES; box++) {

                if(Math.random() < .66) {
                    // ~2/3 of the time put a cart in the slot
                    corral[col][box] = CHECKED_IN;
                } else {
                    // Otherwise leave it empty
                    corral[col][box] = CHECKED_OUT;
                }
            }
        }
        System.out.println("Cart Corral Initialized");
    }

    /**
     * Whether or not this slot has a cart
     * @param col
     * @param box
     * @return
     */
    public static boolean hasCart(int col, int box) {
        return corral[col][box] == CHECKED_IN;
    }

    /**
     * Finds the coordinates of the first cart. Returns a point
     * whose X value represents a column and whose Y value
     * represents a box
     */
    protected static Point findFirstCart() {
        for(int col = 0; col < NUM_COLUMNS; col++) {
            for(int box = 0; box < NUM_BOXES; box++) {
                if(corral[col][box] == CHECKED_IN) {
                    return new Point(col, box);
                }
            }
        }
        return null;
    }

    /**
     * Finds the coordinates of the first empty box. Returns a point
     * whose X value represents a column and whose Y value
     * represents a box
     */
    protected static Point findFirstEmptyBox() {
        for(int col = 0; col < NUM_COLUMNS; col++) {
            for(int box = 0; box < NUM_BOXES; box++) {
                if(corral[col][box] == CHECKED_OUT) {
                    return new Point(col, box);
                }
            }
        }
        return null;
    }

    protected static void setHasCart(int col, int box, boolean b) {
        int status = CHECKED_OUT;
        if(b) {
            status = CHECKED_IN;
        }
        corral[col][box] = status;
    }
}
