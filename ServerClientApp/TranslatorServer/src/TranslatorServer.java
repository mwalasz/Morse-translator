import gr5.walasz.morsetranslator.controller.ServerController;

/**
 * Main class of the project. Starts server controller.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class TranslatorServer {
    
    public static void main(String args[]){
        try {
            ServerController controller = new ServerController();
            controller.run();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
