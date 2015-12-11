package com.nerdery.smartcarte.client;

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

    /** How long in milliseconds we will wait before timing out */
    public static final int TIMEOUT = 10000;

    /** Client's socket connection */
    private DatagramSocket clientSocket;

    public SmartClient() throws IOException {

        // Create the socket
        clientSocket = new DatagramSocket();
        sendMessage("Hello!");
    }

    /**
     * Attempts to establish a connection to the server
     *
     * @throws UnknownHostException
     */
    public void sendMessage(String message) throws IOException {

        System.out.println("Trying to establish a connection to : " + SERVER_HOST + " : " + SERVER_PORT);

        byte[] messageData = message.getBytes();
        byte[] responseData = new byte[256];

        // Create the hosts inetaddress
        InetAddress hostAddress = InetAddress.getByName(SERVER_HOST);

        // Construct the packet to send
        DatagramPacket messagePacket = new DatagramPacket(messageData, messageData.length, hostAddress, SERVER_PORT);

        // Send the message
        clientSocket.send(messagePacket);
        System.out.println("Message sent! \n");

        // Create a response receptacle
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
        System.out.println("Waiting for response...");

        clientSocket.setSoTimeout(TIMEOUT);

        try {

            clientSocket.receive(responsePacket);
            System.out.println("Received response: " + new String(responsePacket.getData(), 0, responsePacket.getLength()));
        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out.");
        }
    }
}
