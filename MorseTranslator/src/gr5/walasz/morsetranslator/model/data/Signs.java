package gr5.walasz.morsetranslator.model.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Type that holds mappings for specific special signs.
 *
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public final class Signs {
    
    /**
     *  Normal signs mapped to morse strings.
     */
    private final Map<Character, String> normalSignsMappedToMorse;
    
    /**
     *  Morse strings mapped to normal signs.
     */
    private final Map<String, Character> morseSignsMappedToNormal;
    
    /**
     * Array of normal signs.
     */
    private final char[] normalSigns = { 
        '.', ',', '!', '?', ':', ' '
    };
    
    /**
     * Array of morse equivalents of normal signs.
     */
    private final String[] morseSigns = { 
        ".-.-.-", "--..--", "-.-.--", "..--..", "---...", "......."
    };
    
    /**
     *  Morse string that contains space equivalent.
     */
    private final String space;
    
    /**
     *  Morse string that contains dot equivalent.
     */
    private final String dot;

    /**
     * Constructor that initializes maps of characters.
     */
    public Signs(){
        this.normalSignsMappedToMorse = new HashMap<>();
        this.morseSignsMappedToNormal = new HashMap<>();
        
        for (int i = 0; i < normalSigns.length; ++i) {
            normalSignsMappedToMorse.put(normalSigns[i], morseSigns[i]);
            morseSignsMappedToNormal.put(morseSigns[i], normalSigns[i]);
        }
        
        this.space = normalSignsMappedToMorse.get(' ');
        this.dot = normalSignsMappedToMorse.get('.');
    }
        
    /**
     * Getter.
     * 
     * @return value of space character
     */
    public String getMorseSpaceSign(){
        return space;
    }
    
    /**
     * Getter.
     * 
     * @return value of dot character
     */
    public String getMorseDotSign(){
        return dot;
    }
    
    /**
     * Method that returns morse string for specific sign.
     * 
     * @param sign character whose Morse equivalent is desirable
     * @return Morse string that is equal to sign
     */
    public String getMorseSign(char sign){
        return normalSignsMappedToMorse.get(sign);
    }
    
    /**
     * Method that returns normal sign for specific morse string.
     * 
     * @param morse morse string whose normal sign equivalent is desirable
     * @return normal sign or zero if mappings don't have corresponding sign
     */
    public char getNormalSign(String morse){
        if (morseSignsMappedToNormal.get(morse.toLowerCase()) != null)
            return morseSignsMappedToNormal.get(morse.toLowerCase());
        else return '0';
    }
}
