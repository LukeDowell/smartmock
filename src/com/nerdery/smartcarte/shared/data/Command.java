package com.nerdery.smartcarte.shared.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created by ldowell on 12/14/15.
 */
public class Command {

    /** Asks for the status of every door */
    public static final int ALL_DOOR_STATUS = 0x01;

    /** Asks for the status of a single door, with a given column and box name */
    public static final int SINGLE_DOOR_STATUS = 0x02;

    /** Asks for the status of items in every box */
    public static final int ALL_ITEM_STATUS = 0x03;

    /** Sent from the server when a given column number is incorrect or nonexistant */
    public static final int WRONG_COLUMN_NUMBER = 0xFE;

    /** Sent from the server when the command's checksum fails */
    public static final int CHECKSUM_FAILED = 0x81;

    /** A CRC32 checksum will always be 8 bytes long */
    private static final int CHECKSUM_LENGTH = 8;


    /**
     * Packages a command into a byte array that can then be sent to
     * the DCB
     *
     * @param command
     *      The command to give. Some commands require arguments
     * @param args
     *      Optional. Args are most often a column and box number
     * @return
     *      A formatted byte array containing the length of the array, the
     *      command, arguments if they were provided, and a checksum of the array
     * @throws IOException
     *
     */
    public static byte[] packageCommand(int command, int... args) throws IOException {

        // Pre calculate total length. length + command (2)  + args + checksum
        int totalLength = 2 + args.length + Command.CHECKSUM_LENGTH;

        // Open up a byte array stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(totalLength);
        bos.write(command);

        // For each command, write to the byte array
        for(int arg : args) {
            bos.write(arg);
        }

        // convert the commands and argument into a byte array
        byte[] commandBytes = bos.toByteArray();

        // Close the byte stream
        bos.close();

        // Create a new checksum and update it against the commands byte array
        Checksum checksum = new CRC32();
        checksum.update(commandBytes, 0, commandBytes.length);

        // Get the sum value and store it in a byte array
        long sum = checksum.getValue();
        byte[] checksumBytes = ByteBuffer.allocate(Long.BYTES).putLong(sum).array();
        byte[] message = new byte[totalLength];

        // Copy both the command array and checksum array into the message array
        System.arraycopy(commandBytes, 0, message, 0, commandBytes.length);
        System.arraycopy(checksumBytes, 0, message, commandBytes.length, checksumBytes.length);

        return message;
    }

    private Command() {}
}
