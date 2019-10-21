package gr5.walasz.morsetranslator.model;

import gr5.walasz.morsetranslator.controller.TranslationMode;
import gr5.walasz.morsetranslator.exceptions.DetectedWrongCharacterInMorseTextException;
import gr5.walasz.morsetranslator.model.data.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that handles app logic and thus translate normal text to Morse and vice versa.
 * 
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public class TranslatorModel {
    
    /**
     * Mapped letters.
     */
    private final Letters letters;
    
    /**
     * Mapped numbers.
     */
    private final Digits numbers;
    
    /**
     * Mapped signs.
     */
    private final Signs signs;
    
    /**
     * Text that is meant to be translated.
     */
    private String textToTranslate;
    
    /**
     * Translated text.
     */
    private String translatedText;
    
    /**
     * Contructor that initializes instances of private members.
     */
    public TranslatorModel()
    {
        letters = new Letters();
        numbers = new Digits();
        signs = new Signs();
    }
    
    /**
     * Method that sets text to translate and resets translated text.
     * 
     * @param textToSet text to translate
     */
    public void setTextToTranslateAndResetTranslatedText(String textToSet){
        this.translatedText = "";
        
        this.textToTranslate = "";
        this.textToTranslate = textToSet.toLowerCase();
    }

    /**
     * Getter.
     * 
     * @return value of translatedText 
     */
    public String getTranslatedText(){
        return this.translatedText;
    }
    
    /**
     * Method that translates passed text using passed type of translation.
     * 
     * @param text text to be translated
     * @param translationMode mode of translation
     * @throws DetectedWrongCharacterInMorseTextException exception thrown when morse text to translate contains forbidden characters
     */
    public void translate(String text, TranslationMode translationMode) throws DetectedWrongCharacterInMorseTextException{
        setTextToTranslateAndResetTranslatedText(text);
        
        switch(translationMode){
            case MORSE_TO_NORMAL:
                try {
                    validateMorseText();
                    convertMorseToText();
                } catch (DetectedWrongCharacterInMorseTextException e) {
                    throw e;
                }
                break;
                
            case NORMAL_TO_MORSE:
                convertTextToMorse();
                break;
                
            default:
                throw new AssertionError(translationMode.name());
        }
    }
    
    /**
     * Method that translates normal text to morse text, character by character.
     */
    private void convertTextToMorse(){
        char[] text = textToTranslate.toCharArray();
        
        for (char character : text){
            addMorseCharacterEquivalent(character);
            addSpaceToText();
        }
    }
    
    
    /**
     * Method that translates morse text to normal text, character by character.
     */
    private void convertMorseToText(){
        String[] morseCharacters = textToTranslate.split("\\s+");
        
        for (String character : morseCharacters){
            addNormalCharacterEquivalent(character);
        }
    }
    
    /**
     * Method that translates single morse string to normal character and adds it to translated text.
     * 
     * @param morse morse string to translate
     */
    private void addNormalCharacterEquivalent(String morse){
        char letter = letters.getNormalLetter(morse);
        if (!Character.isSpaceChar(letter)){
            translatedText += letter;
        }
        else {
            char number = numbers.getNormalDigit(morse);
            if (!Character.isSpaceChar(number)){
                translatedText += number;
            }
            else{
                char sign = signs.getNormalSign(morse);
                if (sign != '0'){
                    translatedText += sign;
                }
            }
        }
    }
    
    /**
     * Method that translates single character to morse string and adds it to translated text.
     * 
     * @param character normal alphaber character to translate
     */
    private void addMorseCharacterEquivalent(char character){
        if (Character.isLetter(character)){
            translatedText += letters.getMorseString(character);
        }
        else if (Character.isDigit(character)){
            translatedText += numbers.getMorseDigit(character);
        }
        else {
            translatedText += signs.getMorseSign(character);
        }
    }
    
    /**
     * Method that add space to translated text.
     */
    private void addSpaceToText(){
         translatedText += " ";
    }
    
    /**
     * Method that checks if morse text contains forbidden characters
     * 
     * @throws DetectedWrongCharacterInMorseTextException exception thrown when forbidden characters are detected
     */
    private void validateMorseText() throws DetectedWrongCharacterInMorseTextException {
        /**
         * Regex pattern that allows only string that has at least one of these characters: '.', '-', ' '.
         */
        Pattern pattern = Pattern.compile("[\\.\\-\\s\\\"]+");
        Matcher matcher = pattern.matcher(textToTranslate);
        
        if (!matcher.matches())
            throw new DetectedWrongCharacterInMorseTextException();
    }
}
