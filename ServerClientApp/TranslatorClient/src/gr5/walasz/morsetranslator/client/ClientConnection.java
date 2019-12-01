package gr5.walasz.morsetranslator.client;

import gr5.walasz.morsetranslator.properties.AppProperties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing connection with server.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class ClientConnection implements Closeable {
    
    /**
     * Socket representing connection to the client.
     */
    private Socket socket;
    
    /**
     * Formatted input character stream from client.
     */
    private PrintWriter clientInput;
    
    /**
     * Buffered output character stream.
     */
    private BufferedReader serverOutput;
    
    /**
     * Application properties.
     */
    private AppProperties properties;
    
    /**
     * Contructor which takes properties from file and starts streams.
     */
    public ClientConnection() {
        properties = new AppProperties();
        
        try {
            socket = new Socket(properties.getAdress(), properties.getPort());
            serverOutput = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            clientInput = new PrintWriter( 
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);                    
        } catch(IOException e) {
            System.err.println("Unable to initialize connection with server because of: " + e.getMessage());            
        }        
    }
    
    /**
     * Sends client's message to server.
     * 
     * @param message message to server
     */
    public void sendMessageToServer(String message) {
        
        clientInput.println(message);      
    }
    
    /**
     * Get message from server.
     * 
     * @return message from server
     */
    public String getMessageFromServer() {
        String msg = "";
        
        try {
            msg = serverOutput.readLine();
            
        } catch(IOException e) {
            System.err.println("Unable to read message from server, reason: " + e.getMessage());
        }
        
       return msg;
    }
    
    /**
     * Skips number of messages, send specific parameters and retrieve answer.
     * 
     * @param amountToSkip number of messages to skip
     * @param paramToSend parameter to sent
     * @return answer from server
     */
    public String skipMessagesSendArgsAndGetAnswer(int amountToSkip, String paramToSend) {
        skipMessages(amountToSkip);
        sendMessageToServer(paramToSend);
        
        return getMessageFromServer();
    }
    
    /**
     * Skips number of messages, send specific parameters and retrieve number of answers.
     * 
     * @param amountToSkip number of messages to skip
     * @param amountOfAnswers number of answers to save
     * @param paramToSend parameter to sent
     * @return list of answers from server
     */
    public ArrayList<String> skipMessagesSendArgsAndGetManyAnswers(int amountToSkip, int amountOfAnswers, String paramToSend) {
        skipMessages(amountToSkip);
        sendMessageToServer(paramToSend);
        
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; i < amountOfAnswers; i++) {
          answers.add(getMessageFromServer());
        }
        
        return answers;
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
    
    /**
     * Skips number of messages from server.
     * 
     * @param amountToSkip number of messages to skip
     */
    private void skipMessages(int amountToSkip) {
        for (int i = 0; i < amountToSkip; i++) {
            getMessageFromServer();
        }
    }
}
