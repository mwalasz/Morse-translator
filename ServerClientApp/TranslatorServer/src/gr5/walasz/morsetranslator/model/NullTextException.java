/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr5.walasz.morsetranslator.model;

/**
 * Exception thrown when text passed to translation is null.
 * 
 * @author Mateusz Walasz
 * @version 1.2.0
 */
public class NullTextException extends Exception {
        
    public NullTextException(String message) {
        super(message);
    }
    
    public NullTextException() {
        super("Test to translate cannot be null!");
    }
}
