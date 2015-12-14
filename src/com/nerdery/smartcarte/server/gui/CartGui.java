package com.nerdery.smartcarte.server.gui;

import javax.swing.*;

/**
 * Created by ldowell on 12/14/15.
 */
public class CartGui extends JFrame {

    public CartGui() {
        initComponents();
    }

    /** Sets up the frame, does not set to visible */
    private void initComponents() {
        setTitle("Cart Corral");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
