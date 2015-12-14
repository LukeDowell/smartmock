package com.nerdery.smartcarte.server;

import com.nerdery.smartcarte.shared.data.Command;

/**
 * Created by ldowell on 12/14/15.
 */
public class CommandController {

    /**
     * Attempts to execute upon a given message
     * @param message
     *      The message to process
     * @return
     *      A byte array response that can be sent back to the client
     */
    public static byte[] process(byte[] message) {

        // Convert to int so intellij stops crying
        int commandFlag = message[1];

        // This doesn't feel good. How else to go about this?
        switch(commandFlag) {

            case Command.ALL_DOOR_STATUS:
                break;

            case Command.ALL_ITEM_STATUS:
                break;

            case Command.CHECKSUM_FAILED:
                break;

            case Command.SINGLE_DOOR_STATUS:
                break;

            default:
                break;
        }

        return null;
    }
}
