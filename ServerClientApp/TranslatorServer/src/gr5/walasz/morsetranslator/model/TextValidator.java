package gr5.walasz.morsetranslator.model;

/**
 * Interface defining method for morse text validation
 * 
 * @author Mateusz Walasz
 * @version 1.1.0
 */
public interface TextValidator {
    void validate(String text) throws DetectedWrongCharacterInMorseTextException;
}
