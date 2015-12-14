package com.nerdery.smartcarte;

import com.nerdery.smartcarte.client.SmartClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * Created by ldowell on 12/11/15.
 */
public class ClientMain {

    public static void main(String[] args) {
        try {
            new SmartClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

