/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication4;

/**
 *
 * @author Dad
 */
public class Anagram {
    // scrambled words
    private static final String scrambled[] = {
        "Votirc",
        "Khikos",
        "cportemu",
        "nrsewokt",
        "oktsec",
        "rvrsee",
        "wlfo"};
    private static final String correct[] = {
        "Victor",
        "Kishko",
        "computer",
        "networks",
        "socket",
        "server",
        "wolf"};
    //messages
    private static final String messages[] =
    {"Scrambled word: \n",       
     "your answer: \n",  
     "The answer is incorrect. Try again\n",
     "The answer is correct. Want to try another word? "
     + "(Press 'q'+<enter> to quit or <any letter>+<enter> to continue)\n"};    

    

    Anagram (){};
    /**
     * Gets the message at a given index .
     * @param index of required message
     * @return message at that index 
     */
    public String getMessage (int index){
        return messages[index];
    }
    
    /**
     * Gets the word at a given index.
     * @param idx index of required word
     * @return word at that index in its natural form
    */
    public String getWord (int index){
        return correct[index];
    }

    /**
     * Gets the word at a given index in its scrambled form.
     * @param idx index of required word
     * @return word at that index in its scrambled form
     */
    public String getScrambledWord (int index){
        return scrambled[index];
    }

    /**
     * Checks whether a user's guess for a word at the given index is correct.
     * @param index of the word guessed
     * @param userGuess the user's guess for the actual word
     * @return true if the guess was correct; false otherwise
     */
    public boolean isCorrect(int index, String userGuess) {
        return userGuess.equals(getWord(index));
    }
}
