package gr5.walasz.morsetranslator.translatorclient;

import gr5.walasz.morsetranslator.clientcontroller.ClientController;

/**
 * Main client class.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0 
 */
public class TranslatorClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ClientController controller = new ClientController();
        controller.run();
    }
}
