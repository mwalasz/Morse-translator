package gr5.walasz.morsetranslator.exceptions;

/**
 * Exception informing about wrong parameters passed to the program.
 * 
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public class InvalidInitParamsException extends Exception {
    
    public InvalidInitParamsException(String message) {
        super(message);
    }
    
    public InvalidInitParamsException() {
        super("Podano niewłaściwe parametry.");
    }
}
