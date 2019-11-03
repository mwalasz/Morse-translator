package gr5.walasz.morsetranslator;

import gr5.walasz.morsetranslator.controller.TranslatorController;
import gr5.walasz.morsetranslator.model.DetectedWrongCharacterInMorseTextException;
import gr5.walasz.morsetranslator.exceptions.InvalidInitParamsException;
import gr5.walasz.morsetranslator.model.TranslatorModel;
import gr5.walasz.morsetranslator.view.TranslatorView;

/**
 * Main class of the application.
 * 
 * @author Mateusz Walasz
 * @version 1.0.0
 */
public class MorseTranslator {
    
    private static TranslatorModel model;
    private static TranslatorController controller;
    
    /**
     * @param args the command line arguments
     * 
     * Application require two parameters to start. 
     * First one has to be type of translation: 
     *      -m for morse to normal text or -n for normal to morse.
     * Second one need to be text that will be translated.
     * 
     * Text to be translated from Morse has to contatin only '.', '-' and ' '. 
     */
    public static void main(String[] args) throws InvalidInitParamsException {
        controller = TranslatorController.tryParseParams(args);
        
        if (controller != null) {
            model = new TranslatorModel();
            
            try {
                model.translate(controller.getTextToTranslate(), controller.getModeFlag());
                TranslatorView.displayOperationDescription(controller.getTextToTranslate(), model.getTranslatedText(), controller.getModeFlag());    
            } catch (DetectedWrongCharacterInMorseTextException e) {
                TranslatorView.displayExceptionDescription(e);
            }
        
        }
        else {
            TranslatorView.displayHelp();
        }
    }
}
