package gr5.walasz.morsetranslator.view;

import gr5.walasz.morsetranslator.controller.TranslationMode;

/**
 * Class that handles communication with application user.
 * Displays necessary information to console.
 * 
 * @author Mateusz Walasz
 * @version 1.0.1
 */
public final class TranslatorView {

    /**
     * Method that displays to the console description of performed type of translation.
     * 
     * @param textToTranslate text that is intended to be translated
     * @param translatedText text that has been translated
     * @param mode choosed type of translation
     */
    public final static void displayOperationDescription(String textToTranslate, String translatedText, TranslationMode mode) {
        switch(mode){
            case MORSE_TO_NORMAL:
                System.out.println("Tekst zapisany morsem: " + textToTranslate);
                displayTranslatedText(translatedText);
                break;
                
            case NORMAL_TO_MORSE:
                System.out.println("Tekst normalnie zapisany: " + textToTranslate);
                displayTranslatedText(translatedText);
                break;
                
            default:
                throw new AssertionError(mode.name());
        }
    }
    
    /**
     *  Method that displays to the console information about application usage.
     */
    public final static void displayHelp(){
        System.out.println("\nW celu poprawnego uruchomienia aplikacji wprowadź: "
                + "\n - przełącznik: "
                + "\n    -m - by wybrać tłumaczenie z Morse'a"
                + "\n    -n - by wybrać tłumaczenie na Morse'a"
                + "\n - tekst przeznaczony do tłumaczenia podany w cudzysłowach."
                + "\n\nPrzykładowe uruchomienie programu: -n \"Ala ma kota.");    
    }
    
    /**
     * Method that displays description of particular exception.
     * 
     * @param e exception which informations are meant to be displayed
     */
    public final static void displayExceptionDescription(Exception e) {
        System.out.println(e.getMessage());
    }
    
    /**
     * Method that prompts user to enter translation mode.
     */
    public static void askForTranslationMode(){
        System.out.print("Wpisz m|M w celu przetłumaczenia tekstu z alfabetu Morse'a, \nlub n|N w celu przetłumaczenia normalnego tekstu: ");
    }
    
    /**
     * Method that prompts user to enter text to translate.
     */
    public static void askForText(){
        System.out.println("Podaj tekst do przetłumaczenia: ");
    }
    
    /**
     * Method that displays to the console translated text.
     * 
     * @param translatedText translated text
     */
    private static void displayTranslatedText(String translatedText){
        System.out.println("Tekst przetłumaczony: " + translatedText);
    }
}
