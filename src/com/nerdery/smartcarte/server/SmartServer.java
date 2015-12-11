package com.nerdery.smartcarte.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by ldowell on 12/11/15.
 */
public class SmartServer {

    public static final int SERVER_PORT = 8008;

    /** The server's socket connection */
    private DatagramSocket serverSocket;

    /**
     * Creates a new server
     * @throws SocketException
     *      Thrown if the provided port is already occupied
     */
    public SmartServer() throws IOException {
        // Create the datagram socket
        serverSocket = new DatagramSocket(SERVER_PORT);
        execute();
    }

    private void execute() throws IOException {

        System.out.println("Server listening on port: " + SERVER_PORT);

        while(!Thread.currentThread().isInterrupted()) {

            // Is this just a container that the data is written to?
            byte[] receivedData = new byte[256];


            // Build the incoming packet
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);

            // Wait until a packet comes in
            serverSocket.receive(receivedPacket);

            // Store the address from which we received the packet
            InetAddress clientAddress = receivedPacket.getAddress();

            // This is a deceptively important step
            // If you don't pull the client's port, the server will send messages back to itself over and over
            int clientPort = receivedPacket.getPort();

            // Convert the byte array to a string. Without the 2nd and 3rd arguments, all the empty bytes are displayed
            String clientMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());

            System.out.println("Received packet from : " + clientAddress);
            System.out.println("Data's message: " + clientMessage);

            byte[] response = "HELLO".getBytes();

            DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
            serverSocket.send(responsePacket);
        }
    }
}
