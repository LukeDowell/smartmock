package com.nerdery.smartcarte.client;

import com.nerdery.smartcarte.shared.data.Command;

import java.io.IOException;
import java.net.*;

/**
 * Created by ldowell on 12/11/15.
 */
public class SmartClient {

    /** The host's address */
    public static final String SERVER_HOST = "127.0.0.1";

    /** The server's port */
    public static final int SERVER_PORT = 8008;
    public static final int CLIENT_PORT = 7007;

    /** How long in milliseconds we will wait before timing out */
    public static final int TIMEOUT = 10000;

    /** Client's socket connection */
    private DatagramSocket writeSocket;
    private DatagramSocket readSocket;

    /** The server's inet address */
    private InetAddress serverAddress;

    public SmartClient() throws IOException {

        // Create the inetaddress
        serverAddress = InetAddress.getByName(SERVER_HOST);

        readSocket = new DatagramSocket(CLIENT_PORT);
        writeSocket = new DatagramSocket();

        // TODO: Move
        new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {

                // Create the response buffer
                byte[] responseBuffer = new byte[256];

                // Create the response packet
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

                try {

                    readSocket.receive(responsePacket);
                    System.out.println("Message received: "  + new String(responsePacket.getData()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        sendMessage(Command.packageCommand(Command.ALL_DOOR_STATUS));
    }

    /**
     * Attempts to send a message to the server
     * @param message
     *      The command message to send
     */
    public boolean sendMessage(byte[] message) {
        System.out.println("Sending message: ");
        for(byte b : message) {
            System.out.print(b);
        }
        System.out.println("");

        // Build the packet we are going to send
        DatagramPacket messagePacket = new DatagramPacket(message, 0, message.length, serverAddress, SERVER_PORT);

        try {

            writeSocket.send(messagePacket);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}