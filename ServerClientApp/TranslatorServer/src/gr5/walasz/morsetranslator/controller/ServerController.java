package gr5.walasz.morsetranslator.controller;

import gr5.walasz.morsetranslator.controller.TranslationMode;
import gr5.walasz.morsetranslator.model.TranslatorModel;
import java.io.IOException;
import java.net.Socket;
import gr5.walasz.morsetranslator.server.Server;
import gr5.walasz.morsetranslator.server.SingleService;

/**
 *
 * @author Mateusz Walasz
 */
public class ServerController {
    private SingleService service;
    private Server server;
    private Socket socket;
    private final TranslatorModel translator;
    
    private TranslationMode translationMode = null;
    private boolean toCloseConnection = false;
    
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
    
    private String getTextForTranslation() throws IOException{
        service.sendMessageToClient("\nEnter text to translate: ");
        
        return service.getClientInput();
    }
    
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
    
    private void sendInitialMessage(){
        service.sendMessageToClient("Welcome to Morse Translator Server!");
    }
    
    private void sendHelp(){
        service.sendMessageToClient("\nHelp info:");
        service.sendMessageToClient("    start -> -m (from morse to normal) or -n (from normal to morse) -> 'text to translate'");
    }
    
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
    
    private void handleRunException(Exception e){
        toCloseConnection = true;
        if (e == null)
            System.out.println("Error occurred: " + e.getMessage());
        else System.out.println("Client unexpectedly closed connection.");
    }
    
    private void closeServer(){
        try { 
            this.server.close();
            System.out.println("Server successfully closed.");        
        } catch(IOException e) {
            System.err.println("Unable to close server because of: " + e.getMessage());
        } 
    }
    
    private void closeService(){
        try { 
            this.service.close();
            System.out.println("Service successfully closed.");        
        } catch(IOException e) {
            System.err.println("Unable to close service because of: " + e.getMessage());
        }
    }
}
