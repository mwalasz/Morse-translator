package gr5.walasz.morsetranslator.controller;

import gr5.walasz.morsetranslator.exceptions.InvalidInitParamsException;
import gr5.walasz.morsetranslator.view.TranslatorView;
import java.util.Scanner;

/**
 * Class that takes data from the user at the start of the application.
 * 
 * @author Mateusz Walasz
 * @version 1.0.1
 */
public class TranslatorController {
    
    /**
     * Selected type of translation.
     */
    private TranslationMode translationMode;
    
    /**
     * Text to translate.
     */
    private String textToTranslate;
    
    /**
     * Constructor that initializes class if passed parameters contains 2 arguments.
     * 
     * @param args params passed by application user
     * @throws InvalidInitParamsException exception thrown when wrong parameters were passed to the application
     */
    public TranslatorController(String[] args) throws InvalidInitParamsException{
        if (args.length == 2){
            setOperation(args[0]);
            this.textToTranslate = args[1];
        }
        else if (args.length == 0){
            getUserInput();
        }
        else {
            throw new InvalidInitParamsException();
        }
    }
    
    /**
     * Getter.
     * 
     * @return value of textToTranslate
     */
    public String getTextToTranslate(){
        return this.textToTranslate;
    } 
    
    /**
     * Getter.
     * 
     * @return value of translationMode
     */
    public TranslationMode getModeFlag(){
        return this.translationMode;
    }

    /**
     * Method that parse parameters passed to application.
     * 
     * @param args array of parameters passed to app
     * @return new instance of this class or null if exception was catched
     */
    public static TranslatorController tryParseParams(String[] args) {
        try {
            return new TranslatorController(args);
        } catch(InvalidInitParamsException e){
            TranslatorView.displayExceptionDescription(e);
        }
        
        return null;
    }
    
    /**
     * Method that validates flag passed by user.
     * 
     * @param flag flag passed by user at the start of the application
     * @throws InvalidInitParamsException exception thrown when flag is not recognised
     */
    private void setOperation(String flag) throws InvalidInitParamsException {
        if ("-m".equalsIgnoreCase(flag)){
            this.translationMode = TranslationMode.MORSE_TO_NORMAL;
        }
        else if ("-n".equalsIgnoreCase(flag)){
            this.translationMode = TranslationMode.NORMAL_TO_MORSE;
        }
        else throw new InvalidInitParamsException();
    }
    
    /**
     * Method that handles user input of parameters
     */
    private void getUserInput() throws InvalidInitParamsException{
        Scanner in = new Scanner(System.in);
        
        TranslatorView.askForTranslationMode();
        String mode = in.nextLine();
        
        setOperation("-" + mode);
        
        TranslatorView.askForText();
        String text = in.nextLine();
        
        if (text.isEmpty())
            throw new InvalidInitParamsException();
        else this.textToTranslate = text;
    }
}
