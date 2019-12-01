package gr5.walasz.morsetranslator.clientcontroller;

import gr5.walasz.morsetranslator.client.ClientConnection;
import gr5.walasz.morsetranslator.clientview.TranslatorView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller for client operations.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
*/
public class ClientController {
    
    /**
     * Field representating client's connection to server.
     */
    private ClientConnection connection;
    
    /**
     * Parameters for server oeprations.
     */
    private List<String> parameters;
    
    /**
     * Parameters for server oeprations.
     */
    private List<String> serverAnswers;
    
    /**
     * Scanner for taking user's input.
     */
    private Scanner scanner;
    
    /**
     * Field representing if server is supposed to quit.
     */
    private boolean toCloseConnection = false;
    
    /**
     * Field representing if server performed translation before.
     */
    private boolean isFirstTranslation = true;
    
    /**
     * Controller which initializes inner objects.
     */
    public ClientController() {
        parameters = new ArrayList<String>();
        serverAnswers = new ArrayList<String>();
        scanner = new Scanner(System.in).useDelimiter("\n");
    }
    
    /**
     * Main method which starts and carries out communication between server and client.
     */
    public void run() {
        connection = new ClientConnection();
        
        if (connection != null){
            do {
                if (getParameters()){
                    if (parameters.size() == 3){
                        processingDataOnServerAndGetOutput();
                        TranslatorView.displayLogFromComunication(parameters, serverAnswers);

                        askForQuitAfterDoneProcess();
                    }
                    else {
                        askForHelp();
                    }
                }
                else {
                    askForHelp();
                }
            } while (!toCloseConnection);
        }
    }
    
    /**
     * Processes data on server and gets answers from it.
     */
    private void processingDataOnServerAndGetOutput() {
        String answer;
        ArrayList<String> answers;
        
        System.out.println("Zaczęto przetwarzać żądanie...\n");
        
        for (String param : parameters) {           
            if (param.contains("start")) {
                answer = startConnection(param);
                serverAnswers.add(answer);
            }
            else if (param.equals(parameters.get(parameters.size() - 1))){
                answers = connection.skipMessagesSendArgsAndGetManyAnswers(1, 4, param);
                serverAnswers.addAll(answers);
            }
            else if (param.contains("-")) {
                answer = connection.skipMessagesSendArgsAndGetAnswer(1, param);
                serverAnswers.add(answer);
            }
        }
    }
    
    /**
     * Method which sends starting query to server. 
     * If it is first time it has to skip two messages (welcoming message), 
     * but later there is only one message to skip.
     * 
     * @param param message to send to server
     * @return answer from server
     */
    private String startConnection(String param){
        String answer;
        
        if (isFirstTranslation){
            answer = connection.skipMessagesSendArgsAndGetAnswer(2, param);
            isFirstTranslation = false;
        }
        else answer = connection.skipMessagesSendArgsAndGetAnswer(1, param);
        
        return answer;
    }
    
    /**
     * Retrieves parameters needed for translation on server.
     * 
     * @return boolean value which indicates if method successfully did its job.
     */
    private boolean getParameters(){
        parameters.clear();
        serverAnswers.clear();
        
        if (askForMode()){
            askForText();
            
            parameters.add(0, "start");
            return true;
        }
        
        return false;   
    }
    
    /**
     * Informs user about wrong input. Checks if client want to display help or quit server.
     */
    private void askForHelp(){
        System.out.println("Złe dane wejściowe. By wyświetlić pomoc wpisz 'tak', lub by wyjść 'nie'.");
        String input = scanner.next();

        if (input.equalsIgnoreCase("tak")){
            connection.sendMessageToServer("help");
            System.out.println(connection.getMessageFromServer());
        }
        else if (input.equalsIgnoreCase("nie")){
            quitServer();
        }
    }
    
    /**
     * Asks if to continue comunication with server or to end.
     */
    private void askForQuitAfterDoneProcess() {
        System.out.println("Pomyślnie zakończono proces. Aby kontynuować wpisz cokolwiek lub by wyjść 'koniec'.");
        String input = scanner.next();

        if (input.equalsIgnoreCase("koniec")){
            quitServer();
        }
    }
    
    /**
     * Ask client for text to translate.
     * 
     * @return boolean value which indicates if method successfully did its job.
     */
    private void askForText() {
        TranslatorView.askForText();
        String text = scanner.next();
        
        parameters.add(text);
    } 
    
    /**
     * Ask client for translation mode.
     * @return boolean value which indicates if method successfully did its job.
     */
    private boolean askForMode() {
        TranslatorView.askForTranslationMode();
        String mode = scanner.next();
        if (mode.equalsIgnoreCase("m") || mode.equalsIgnoreCase("n")){
            parameters.add("-" + mode);
            return true;
        }
        else return false;
    }
    
    /**
     * Quits connectionwith server.
     */
    private void quitServer() {
        toCloseConnection = true;
        connection.sendMessageToServer("quit");
        System.out.println("Zakończono połączenie.");
    }
}
