package org.lukedowell.carte.shared;

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

    private Command() {}
}