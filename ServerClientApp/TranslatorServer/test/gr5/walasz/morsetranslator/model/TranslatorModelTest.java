package gr5.walasz.morsetranslator.model;

import gr5.walasz.morsetranslator.controller.TranslationMode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for public metods of TranslatorModel class
 * 
 * @author Mateusz Walasz
 * @version 1.3.0
 */
public class TranslatorModelTest {
    
    TranslatorModel translator;
    
    @Before
    public void setup() {
        translator = new TranslatorModel();
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToNormal() throws Exception {
        String textToTranslate = ".- .-.. .- ....... -- .- ....... -.- --- - .-";
        
        translator.translate(textToTranslate, TranslationMode.MORSE_TO_NORMAL);

        assertEquals("ala ma kota", translator.getTranslatedText());
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToNormalEmptyText() throws Exception {
        String textToTranslate = "";
        
        translator.translate(textToTranslate, TranslationMode.MORSE_TO_NORMAL);

        assertEquals("", translator.getTranslatedText());
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToNormalNullText() throws NullTextException, DetectedWrongCharacterInMorseTextException {
        try {
            String textToTranslate = null;

            translator.translate(textToTranslate, TranslationMode.MORSE_TO_NORMAL);

            fail("An exception should be thrown when null text to translate is passed.");
        } catch (NullTextException e) {
        }
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToMorse() throws Exception {
        String textToTranslate = "kot ma ale";
        
        translator.translate(textToTranslate, TranslationMode.NORMAL_TO_MORSE);

        assertEquals("-.- --- - ....... -- .- ....... .- .-.. . ", translator.getTranslatedText());
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToMorseEmptyText() throws Exception {
        String textToTranslate = "";
        
        translator.translate(textToTranslate, TranslationMode.NORMAL_TO_MORSE);

        assertEquals("", translator.getTranslatedText());
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     */
    @Test
    public void testTranslateToMorseNullText() throws NullTextException, DetectedWrongCharacterInMorseTextException {
        try {
            String textToTranslate = null;

            translator.translate(textToTranslate, TranslationMode.NORMAL_TO_MORSE);

            fail("An exception should be thrown when null text to translate is passed.");
        } catch (NullTextException e) {
        }
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     * Checks method behaviour when it detects incorrect characters while translating from Morse.
     */
    @Test
    public void testTranslateToNormalWrongCharacters() throws DetectedWrongCharacterInMorseTextException, NullTextException {
        try {
            String textToTranslate = "asdasd";
            
            translator.translate(textToTranslate, TranslationMode.MORSE_TO_NORMAL);
            
            fail("An exception should be thrown when the incorrect characters are detected");
        } catch (DetectedWrongCharacterInMorseTextException e) {
        }
    }
    
    /**
     * Test of translate method, of class TranslatorModel.
     * Checks method behaviour when unknown mode is selected
     */
    @Test
    public void testTranslateUnknownMode() throws AssertionError {
        try {
            String textToTranslate = "Ala ma kota";
            
            translator.translate(textToTranslate, null);
            
            fail("An exception should be thrown when the unknown mode is selected");
        } catch (Exception e) {
        }
    }
}
