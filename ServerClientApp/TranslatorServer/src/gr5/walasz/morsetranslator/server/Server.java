package gr5.walasz.morsetranslator.server;

import gr5.walasz.morsetranslator.controller.ServerController;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server class responsible for connecting and communicating with client's application. 
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class Server implements Closeable {
    
    /**
     * Field representing the socket waiting for client connections
     */
    private ServerSocket serverSocket;
    
    /**
     * Application properties that read from file server's port.
     */
    private final AppProperties properties;
    
    public Server() throws IOException {
        properties = new AppProperties();
    }
    
    /**
     * Method to start server.
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(properties.getPort());
            System.out.println("Server started at port " + properties.getPort() + ".");
        } catch (IOException e) {
            System.err.println("When starting server following error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Accepts server socket.
     * 
     * @return socket for server
     * @throws IOException 
     */
    public Socket acceptServerSocket() throws IOException{
        return this.serverSocket.accept();
    }
    
    /**
     * Implementation of closing connection through a socket.
     * 
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
