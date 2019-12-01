package gr5.walasz.morsetranslator.model.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Type that holds mappings for each letter. 
 * 
 * @author Mateusz Walasz
 * @version 1.0.0 
 */
public final class Letters {    
    
    /**
     *  Normal letters mapped to morse strings.
     */
    private final Map<Character, String> normalLettersMappedtoMorse;
    
    /**
     *  Morse strings mapped to normal letters.
     */
    private final Map<String, Character> morseStringMappedToNormalLetter;
    
    /**
     * Array of normal letters.
     */
    private final char[] normalLetters = { 
        'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u',
        'v', 'w', 'x', 'y', 'z'
    };
    
    /**
     * Array of morse equivalents of normal letters.
     */
    private final String[] morseLetters = { 
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
        "....", "..", ".---", "-.-", ".-..", "--", "-.",
        "---", ".--.", "--.-", ".-.", "...", "-", "..-",
        "...-", ".--", "-..-", "-.--", "--.."
    };
    
    /**
     * Constructor that initializes maps of characters.
     */
    public Letters(){
        this.normalLettersMappedtoMorse = new HashMap<>();
        this.morseStringMappedToNormalLetter = new HashMap<>();
        
        for (int i = 0; i < normalLetters.length; ++i) {
            normalLettersMappedtoMorse.put(normalLetters[i], morseLetters[i]);
            morseStringMappedToNormalLetter.put(morseLetters[i], normalLetters[i]);
        }
    }
    
    /**
     * Method that returns morse string for specific letter.
     * 
     * @param letter character whose Morse equivalent is desirable
     * @return Morse string that is equal to letter
     */
    public String getMorseString(char letter){
        return normalLettersMappedtoMorse.get(Character.toLowerCase(letter));
    }
    
    /**
     * Method that returns normal letter for specific morse string.
     * 
     * @param morse morse string whose normal letter equivalent is desirable
     * @return character from normal alphabet or space sign if mappings don't have corresponding letter
     */
    public char getNormalLetter(String morse){
        if (morseStringMappedToNormalLetter.get(morse.toLowerCase()) != null)
            return morseStringMappedToNormalLetter.get(morse.toLowerCase());
        else return ' ';
    }
}
