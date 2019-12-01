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
 * Class that handles single connection to server.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class SingleService implements Closeable {
    
    /**
     * Socket representing connection to the client.
     */
    private Socket socket;
    
    /**
     * Formatted output character stream.
     */
    private PrintWriter serverOutput;
    
    /**
     * Buffered input character stream from client.
     */
    private BufferedReader clientInput;
    
    
    /**
     * Contructor which iniatializes instance of class.
     * 
     * @param socket established connection
     * @throws IOException 
     */
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

    /**
     * Takes input from client.
     * 
     * @return client's input 
     * @throws IOException 
     */
    public String getClientInput() throws IOException{
        String clientsMessage = null;
        
        try {
            clientsMessage = this.clientInput.readLine();            
        } catch(IOException e) {
            throw e;
        }
        
        return clientsMessage;
    }
    
    /**
     * Sends message to client.
     * 
     * @param message message for client to send
     */
    public void sendMessageToClient(String message) {
        try {
            serverOutput.println(message);

        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Closes socket.
     * 
     * @throws IOException 
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
