/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr5.walasz.morsetranslator.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Mateusz Walasz
 */
public class SingleService implements Closeable{
    private Socket socket;
    private PrintWriter serverOutput;
    private BufferedReader clientInput;
    
    public SingleService(Socket socket) throws IOException {
        this.socket = socket;
        serverOutput = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            socket.getOutputStream())), true);
        clientInput = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
    }
    
    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public String getClientInput() throws IOException{
        String clientsMessage = null;
        
        try {
            clientsMessage = this.clientInput.readLine();            
        } catch(IOException e) {
            throw e;
        }
        
        return clientsMessage;
    }
    
    public void sendMessageToClient(String message) {
        try {
            serverOutput.println(message);

        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
