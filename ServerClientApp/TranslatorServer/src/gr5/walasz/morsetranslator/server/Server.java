/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr5.walasz.morsetranslator.server;

import gr5.walasz.morsetranslator.controller.ServerController;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mateusz Walasz
 */
public class Server implements Closeable {
    private ServerSocket serverSocket;
    private final AppProperties properties;
    
    public Server() throws IOException {
        properties = new AppProperties();
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(properties.getPort());
            System.out.println("Server started at port " + properties.getPort() + ".");
        } catch (IOException e) {
            System.err.println("When starting server following error occurred: " + e.getMessage());
        }
    }
    
    public Socket acceptServerSocket() throws IOException{
        return this.serverSocket.accept();
    }
    
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
