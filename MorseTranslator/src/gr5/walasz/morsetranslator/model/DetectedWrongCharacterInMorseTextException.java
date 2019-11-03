package gr5.walasz.morsetranslator.model;

/**
 * Exception informing about situation when Morse text contains forbidden characters.
 * 
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public class DetectedWrongCharacterInMorseTextException extends Exception {
    
    public DetectedWrongCharacterInMorseTextException(String message) {
        super(message);
    }
    
    public DetectedWrongCharacterInMorseTextException() {
        super("Tekst zapisany w alfabecie Morse'a zawiera niedozwolone znaki (tj. inne niż: '.', '-', ' ').");
    }
}
