package org.lukedowell.carte.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by ldowell on 12/16/15.
 */
public class UIActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Hello from UI Activator!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Bye from UI Activator!");
    }
}
