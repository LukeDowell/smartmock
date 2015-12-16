package org.lukedowell.carte.shared;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by ldowell on 12/16/15.
 */
public class Network {

    public static final int SERVER_PORT = 8008;
    public static final String SERVER_HOST = "127.0.0.1";
    public static final String MESSAGE_HOST = "vm://localhost";
    public static final String QUEUE_NAME = "CART.EVENTS";


    /**
     * Packages a command to send over the interblag
     * @param command
     *      The int value of a command
     * @param args
     *      Optional.
     * @return
     *      A byte array containing all information provided and a checksum
     */
    public static byte[] packageCommand(int command, int... args) {
        int totalLength = 3 + args.length; //length + command + arg.length + checksum

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(totalLength);
        bos.write(command);
        for(int arg : args) {
            bos.write(arg);
        }
        // Get the checksum of data entered so far
        byte[] commandBytes = bos.toByteArray();
        byte checksum = getXorChecksum(commandBytes);

        // Then write it
        bos.write(checksum);

        // Spit out the whole thing
        return bos.toByteArray();
    }

    /**
     * Unpackages the contents of a message and turns it into a string
     * @param message
     * @return
     */
    public static String unpackageCommand(byte[] message) {
        int length = message[0];
        int command = (int) message[1];
        int[] args = new int[length - 3]; // Find a way to get rid of the 3
        for(int i = 2; i < length - 1; i++) { //wow this is gross
            args[i - 2] = message[i];
        }

        return command + " " + Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");
    }

    /**
     * @param data
     * @return
     */
    public static byte getXorChecksum(byte[] data) {
        byte[] bytes = Arrays.copyOf(data, data.length);

        for(int i = 0 ; i < bytes.length; i++) {
            bytes[0] ^= bytes[i];
        }

        return bytes[0];
    }

}
