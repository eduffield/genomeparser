/*********************************************************************************
**                       CS200 Final Project - Genome Buddy                     **
**                                                                              **
** PROGRAMMER:        Evan Duffield duff1130@bears.unco.edu                     **
** CLASS:             CS200-005                                                 **
** TERM:              Fall 2022                                                 **
** INSTRUCTOR:        Dr. Christopher Harris                                    **
** SUBMISSION DATE:   12/8/2022                                                 **
**                                                                              **
** DESCRIPTION                                                                  **
**    This program parses through a user-provided dump of their sequenced       **
**    genome, sorts it into an ArrayList with a custom Collection class, and    **
**    either allows the user to print their genes to the terminal, search for   **
**    a specific gene, or see peer-reviewed guesses of chronic illnesses they   **
**    might have.                                                               **
**                                                                              **
** COPYRIGHT:                                                                   **
** This program is copyright (c)2022 Evan Duffield, Dr. Christopher Harris,     **
** with external libraries from https://github.com/fangyidong/json-simple and   **
** https://github.com/zhaofengli/snappy/tree/master/scripts/parsegenotypes.py.  **
**                                                                              **
*********************************************************************************/

/**
 * The StrandBasic class is a general class that stores data for every frame of animation,
 * it stores ANSI terminal escape codes for color output, alongside the variables and 
 * methods every animation frame will need to draw its output correctly.
 *
 * @author Evan Duffield
 * @version 12/8/2022
 */

public class StrandBasic {
    // Escape characters for colored terminal output
    protected final String ANSI_RESET = "\u001B[0m";
    protected final String ANSI_GREEN = "\u001B[32m";
    protected final String ANSI_BLUE = "\u001B[34m";
    protected final String ANSI_YELLOW = "\u001B[33m";
    protected final String ANSI_RED = "\u001B[31m";
    
    // Variables for storing details on how the animation frame should be colored and drawn
    protected String colorOne;
    protected String colorTwo;
    protected int lengthOfBase;

    // Empty constructor
    public StrandBasic() { }

    /* Given the base pair on an allele, will decide what colors each strand
     * will have and in what ratios, before calling printStage() to send it to
     * the terminal.
     * 
     * @param base string representing the base pair at one frame of animation (one row in the data)
     */
    public void printLine(String base, int stage) {
        char basePrimitive = base.charAt(0);
        //switch statement based on base pair read in
        switch (basePrimitive) {
            case 'G':
                colorOne = ANSI_GREEN;
                colorTwo = ANSI_BLUE;
                lengthOfBase = 2;
                break;
            case 'C':
                colorOne = ANSI_BLUE;
                colorTwo = ANSI_GREEN;
                lengthOfBase = 1;
                break;
            case 'T':
                colorOne = ANSI_YELLOW;
                colorTwo = ANSI_RED;
                lengthOfBase = 1;
                break;
            default:
                colorOne = ANSI_RED;
                colorTwo = ANSI_YELLOW;
                lengthOfBase = 2;
                break;
        }
        // Calls below method to print to console.
        printStage(stage);
    }

    /* Using the data from printLine(), printStage() is responsible
     * for printing the specific frame of animation.
     * 
     */
    protected void printStage(int stage) {
        switch(stage) {
            case 0:
                System.out.print("|");
                for (int i = 0; i < lengthOfBase; i++) {
                    System.out.print(colorOne+"="+ANSI_RESET);
                }
                for (int i = 0; i < (3-lengthOfBase); i++) {
                    System.out.print(colorTwo+"="+ANSI_RESET);
                }
                System.out.print("|");
                break;
            case 1:
                System.out.print("\\");
                for (int i = 0; i < lengthOfBase; i++) {
                    System.out.print(colorOne+"="+ANSI_RESET);
                }
                for (int i = 0; i < (3-lengthOfBase); i++) {
                    System.out.print(colorTwo+"="+ANSI_RESET);
                }
                System.out.print("/");
                break;
            case 2:
                System.out.print(" \\");
                System.out.print(colorOne+"="+ANSI_RESET);
                System.out.print("/  ");
                break;
            case 3:
                System.out.print("  /   ");
                break;
            case 4:
                System.out.print(" /");
                System.out.print(colorTwo+"="+ANSI_RESET);
                System.out.print("\\  ");
                break;
            case 5:
                System.out.print("/");
                for (int i = 0; i < lengthOfBase; i++) {
                    System.out.print(colorOne+"="+ANSI_RESET);
                }
                for (int i = 0; i < (3-lengthOfBase); i++) {
                    System.out.print(colorTwo+"="+ANSI_RESET);
                }
                System.out.print("\\");
                break;
            default:
                System.out.print("|");
                for (int i = 0; i < lengthOfBase; i++) {
                    System.out.print(colorOne+"="+ANSI_RESET);
                }
                for (int i = 0; i < (3-lengthOfBase); i++) {
                    System.out.print(colorTwo+"="+ANSI_RESET);
                }
                System.out.print("|");
                break;
        }
    }
}
