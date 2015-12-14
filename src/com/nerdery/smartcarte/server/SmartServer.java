package com.nerdery.smartcarte.server;

import com.nerdery.smartcarte.shared.data.Command;

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
        listen();
    }

    /**
     * Sets the server in a continual loop. The server will listen for any messages coming in
     * and attempt to execute on them.
     *
     * TODO: Sanitize incoming messages
     */
    private void listen() {
        System.out.println("Server listening on port: " + SERVER_PORT);
        while(!Thread.currentThread().isInterrupted()) {

            // Create a message recepticle
            byte[] messageBuffer = new byte[256];
            DatagramPacket messagePacket = new DatagramPacket(messageBuffer, messageBuffer.length);

            try {
                serverSocket.receive(messagePacket);

                // Get the length of the message
                byte[] messageData = messagePacket.getData();
                int length = messageData[0];
                byte[] message = new byte[length];

                // Trim the message down
                System.arraycopy(messageData, 0, message, 0, length);

                // Set up response array
                byte[] response;

                // Complete the checksum verification
                if(validChecksum(message)) {
                    response = CommandController.process(message);
                } else {
                    response = Command.packageCommand(Command.CHECKSUM_FAILED);
                }

                InetAddress clientAddress = messagePacket.getAddress();
                int port = messagePacket.getPort();

                DatagramPacket responsePacket = new DatagramPacket(response, response.length, clientAddress, port);
                serverSocket.send(responsePacket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks to see if the checksum passes on a received message
     * @param message
     *      The message to check
     * @return
     *      True if passed, false otherwise
     */
    private boolean validChecksum(byte[] message) {
        return true;
    }
}
