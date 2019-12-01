package gr5.walasz.morsetranslator.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class that read application properties and share them through getters.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */

public class AppProperties {
    
    /**
     * Port on which server is running.
     */
    private int serverPort;
    
    /**
     * Adress of the server;
     */
    private String serverAdress;
    
    /**
     * Built-in properties;
     */
    private Properties props;
    
    /**
     * Constructor which initializes properties and reads data.
     */
    public AppProperties() {
        props = new Properties();
        readServerPropertiesFromFile();
    }
    
    /**
     * Getter for serverPort.
     * 
     * @return server's port
     */
    public int getPort(){
        return serverPort;
    }
    
    /**
     * Getter for serverAdress.
     * 
     * @return server's adress
     */
    public String getAdress(){
        return serverAdress;
    } 
    
    /**
     * Reads port data from file. If it is not defined, sets it to default = 8888;
     */
    private void readServerPropertiesFromFile(){
        try (FileInputStream in = new FileInputStream(".properties")) {
            props.load(in);
            
            String port = props.getProperty("port", "8888");
            serverPort = Integer.parseInt(port);
            
            String adress = props.getProperty("adress", "127.0.0.1");
            serverAdress = adress;
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
