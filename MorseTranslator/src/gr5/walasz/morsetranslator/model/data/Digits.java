package gr5.walasz.morsetranslator.model.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Type that holds mappings for each digit. 
 * 
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public final class Digits {
    
    /**
     *  Normal digits mapped to morse strings.
     */
    private final Map<Character, String> normalDigitsMappedToMorse;
    
    /**
     *  Morse strings mapped to normal digits.
     */
    private final Map<String, Character> morseDigitsMappedToNormal;
    
    /**
     * Array of normal digits.
     */
    private final char[] normalDigits = { 
        '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9'
    };
    
    /**
     * Array of morse equivalents of normal digits.
     */
    private final String[] morseDigits = { 
        "-----", ".----", "..---", "...--", "....-", ".....", "-....",
        "--...", "---..", "----."
    };
    
    /**
     * Constructor that initializes maps of characters.
     */
    public Digits(){
        this.normalDigitsMappedToMorse = new HashMap<>();
        this.morseDigitsMappedToNormal = new HashMap<>();
        
        for (int i = 0; i < normalDigits.length; ++i) {
            normalDigitsMappedToMorse.put(normalDigits[i], morseDigits[i]);
            morseDigitsMappedToNormal.put(morseDigits[i], normalDigits[i]);
        }
    }
    
    /**
     * Method that returns morse string for specific digit.
     * 
     * @param digit character whose Morse equivalent is desirable
     * @return Morse string that is equal to digit
     */
    public String getMorseDigit(char digit){
        return normalDigitsMappedToMorse.get(digit);
    }
    
    /**
     * Method that returns normal digit for specific morse string.
     * 
     * @param morse morse string whose normal digit equivalent is desirable
     * @return normal digit or space sign if mappings don't have corresponding digit
     */
    public char getNormalDigit(String morse){
        if (morseDigitsMappedToNormal.get(morse.toLowerCase()) != null)
            return morseDigitsMappedToNormal.get(morse.toLowerCase());
        else return ' ';
    }
}
