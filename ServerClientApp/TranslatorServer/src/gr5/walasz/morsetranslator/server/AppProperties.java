/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr5.walasz.morsetranslator.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Mateusz
 */

public class AppProperties {
    private int serverPort;
    private Properties props;
    
    public AppProperties() {
        props = new Properties();
        readPortPropertyFromFile();
    }
    
    public int getPort(){
        return serverPort;
    }
    
    private void readPortPropertyFromFile(){
        try (FileInputStream in = new FileInputStream(".properties")) {
            props.load(in);
            
            String port = props.getProperty("port", "8888");
            serverPort = Integer.parseInt(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
