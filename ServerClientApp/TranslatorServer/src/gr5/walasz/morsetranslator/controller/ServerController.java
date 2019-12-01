package gr5.walasz.morsetranslator.controller;

import gr5.walasz.morsetranslator.controller.TranslationMode;
import gr5.walasz.morsetranslator.model.TranslatorModel;
import java.io.IOException;
import java.net.Socket;
import gr5.walasz.morsetranslator.server.Server;
import gr5.walasz.morsetranslator.server.SingleService;

/**
 * MVC controller for server application.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class ServerController {
    
    /**
     * Field representing single connection.
     */
    private SingleService service;
    
    /**
     * Field representing working server.
     */
    private Server server;
    
    /**
     * Field representing connection to the client.
     */
    private Socket socket;
    
    /**
     * Model of the application which performs main action.
     */
    private final TranslatorModel translator;
    
    /**
     * Mode of translation. Represents if model ought to translate from morse to normal or normal to morse.
     */
    private TranslationMode translationMode = null;
    
    /**
     * Field which states if server should stop working.
     */
    private boolean toCloseConnection = false;
    
    /**
     * Contructor intializes instance of class, establishes socket and connection.
     * 
     * @throws IOException 
     */
    public ServerController() throws IOException {
        server = new Server();
        server.start();

        try {
            socket = server.acceptServerSocket();
            service = new SingleService(socket);
        } catch(IOException e) {
            System.err.println("Failed to initialize ServerController. Reason: " + e.getMessage());             
        }
        
        translator = new TranslatorModel();
    }
    
    /**
     * Main method. Sends initial message and run translation if protocol got correct data.
     */
    public void run(){
        sendInitialMessage();

        while (!toCloseConnection) {
            try {
                if (getStartInfo()){
                    if (getTranslationMode()){
                        String toTranslate = getTextForTranslation();   
                        executeTranslation(toTranslate);
                    }
                }
            } catch (Exception e) {
                handleRunException(e);
            }
        }
        
        closeService();
        closeServer();
    }
    
    /**
     * Method that executes translation.
     * 
     * @param toTranslate text to translate
     */
    private void executeTranslation(String toTranslate){
        try {
            service.sendMessageToClient("\nText to translate saved.");
            
            service.sendMessageToClient("Processing ...");
            translator.translate(toTranslate, translationMode);
            service.sendMessageToClient("Text successfully processed.");
            
            service.sendMessageToClient("\nTranslated text: " + translator.getTranslatedText());

        } catch (Exception e) {
            service.sendMessageToClient("Unable to translate text, because of: " + e.getMessage());
        }
    }

    /**
     * Method which take translation mode from client.
     * 
     * @return boolean filed that informs if method successfully performed its job
     * @throws IOException 
     */
    private boolean getTranslationMode() throws IOException{
        service.sendMessageToClient("\nSelect translation mode:");
        String userInput = service.getClientInput();
        
        if (userInput.equalsIgnoreCase("-m")){
            translationMode = TranslationMode.MORSE_TO_NORMAL;
            service.sendMessageToClient("Selected translation from Morse to Normal.");       
            
            return true;
        }
        else if (userInput.equalsIgnoreCase("-n")){
            translationMode = TranslationMode.NORMAL_TO_MORSE;
            service.sendMessageToClient("Selected translation from Normal to Morse.");
            
            return true;
        }
        else {
            handleHelpAndQuit(userInput);
        }
        
        return false;
    }
    
    /**
     * Method which gather text to translate.
     * 
     * @return text to translate
     * @throws IOException 
     */
    private String getTextForTranslation() throws IOException{
        service.sendMessageToClient("\nEnter text to translate: ");
        
        return service.getClientInput();
    }
    
    /**
     * Method that shows starting info and starts the protocol after getting "start" message.
     * 
     * @return boolean filed that informs if method successfully performed its job
     * @throws IOException 
     */
    private boolean getStartInfo() throws IOException{
        service.sendMessageToClient("\nTo start enter 'start', to show help enter 'help', to quit enter 'quit':");
        String str = service.getClientInput();
        
        if (str.contains(""))
            str = str.substring(str.lastIndexOf("") + 1);
        
        if (str.equalsIgnoreCase("start")){
            return true;
        }
        else {
            handleHelpAndQuit(str);
        }
        
        return false;
    }
    
    /**
     * Sends first message to client after server start.
     */
    private void sendInitialMessage(){
        service.sendMessageToClient("Welcome to Morse Translator Server!");
    }
    
    /**
     * Sends info to the client about app usage.
     */
    private void sendHelp(){
        service.sendMessageToClient("\nHelp info:");
        service.sendMessageToClient("    start -> -m (from morse to normal) or -n (from normal to morse) -> 'text to translate'");
    }
    
    /**
     * Sends info to the client if wrong data was passed to server.
     */
    private void sendInfoAboutWrongInput(){
        service.sendMessageToClient("\nWrong input. Type in \"HELP\" to get info about usage.");
    }
    
    private void handleHelpAndQuit(String input){
        if (input.equalsIgnoreCase("help")){
            sendHelp();
        }
        else {
            if (!input.equalsIgnoreCase("quit"))
                sendInfoAboutWrongInput();
            else toCloseConnection = true;
        }
    }
    
    /**
     * Handles exception in main method and situation when client has unexpectedly closed connection.
     * @param e 
     */
    private void handleRunException(Exception e){
        toCloseConnection = true;
        if (e == null)
            System.out.println("Error occurred: " + e.getMessage());
        else System.out.println("Client unexpectedly closed connection.");
    }
    
    /**
     * Closes server connection.
     */
    private void closeServer(){
        try { 
            this.server.close();
            System.out.println("Server successfully closed.");        
        } catch(IOException e) {
            System.err.println("Unable to close server because of: " + e.getMessage());
        } 
    }
    
    /**
     * Closes service connection.
     */
    private void closeService(){
        try { 
            this.service.close();
            System.out.println("Service successfully closed.");        
        } catch(IOException e) {
            System.err.println("Unable to close service because of: " + e.getMessage());
        }
    }
}
