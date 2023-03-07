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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The GenomeBuddy class is responsible for running the main loop of the program.
 * It has several helper methods for main including makeList(), which generates the
 * ArrayList that contains the user's genome. printGenome() which prints an animated
 * helix to the console, binarySearch() as a recursive impementation for an ArrayList,
 * and querySearch(), meant to get user input from the user before calling binarySearch().
 *
 * @author Evan Duffield
 * @version 12/8/2022
 */

public class GenomeBuddy {

    /* makeList() takes a file object from main() and will return an ArrayList of type <SNP>
     * holding the data of the user's genome for easy access.
     * 
     * @param dnaFile file object containing the user's textfile.
     * @return ArrayList of type <SNP>
     * 
     */
    public static ArrayList<SNP> makeList(File dnaFile) throws FileNotFoundException {
        ArrayList<SNP> returnList = new ArrayList<SNP>();

        Scanner dnaScanner = new Scanner(dnaFile);
        while (dnaScanner.hasNextLine()) {
            String thisLine = dnaScanner.nextLine();
            // makes sure any irrelevent rows with no data are skipped
            if (thisLine.startsWith("rs") && !thisLine.startsWith("rsid")) {
                String[] keys = thisLine.split("\\s+");
                String id = keys[0];
                int ch = Integer.parseInt(keys[1]);
                int pos = Integer.parseInt(keys[2]);
                String a1 =  keys[3];
                String a2 =  keys[4];
                
                // Every row of data becomes its own SNP object to hold the row's content.
                SNP tempLine = new SNP(id, ch, pos, a1, a2);
                returnList.add(tempLine);
            }
        }
        return returnList;
    }


    /* printGenome creates a StrandBasic object, which contains one copy of each animation frame.
     * The class iterates through the user's genome, printing to console a representation of a
     * user's double helix.
     * 
     * @param ArrayList of type <SNP>
     * 
     */
    public static void printGenome(ArrayList<SNP> userGenome) {
        int increment = 0;
        StrandBasic animationFrames = new StrandBasic();
        for (SNP line : userGenome) {
            System.out.print(line.toString() + "\t");
            animationFrames.printLine(line.getAllele1(), increment);
            System.out.print("\t");
            animationFrames.printLine(line.getAllele2(), increment);
            System.out.println();
            
            // Cycles the animation one frame for one row, resets it once last frame is reached
            increment++;
            if (increment > 5) {
                increment = 0;
            }
        }
    }
    
    /* RECURSIVE impementation of binarySearch for an ArrayList. ArrayList is always sorted by
     * Collections.sort in main before called, searches for a rsID query given by the user.
     * 
     * @param listToSearch SORTED ArrayList of type <SNP> to search through
     * @param query specific rsID number to find for the user
     * @param low end of range to search (for recursion), 0 is usually used when first called
     * @param high end of range to search (for recursion), listToSearch.size() - 1 is usually used when first called
     */
    public static int binarySearch(ArrayList<SNP> listToSearch, int query, int low, int high) {
        //checks if range is searchable
        if (low > high) {
            return -1;
        }
        
        // Chooses midpoint of range, gets rsID of that midpoint value
        int index = (low + high) / 2;
        int indexValue = listToSearch.get(index).getRsID();

        // Base case, else recursively call function with a smaller range
        if (indexValue == query) {
            return index;
        } else if (indexValue < query) {
            return binarySearch(listToSearch, query, (index+1), high);
        } else {
            return binarySearch(listToSearch, query, low, (index-1));
        }
    }

    /* Helper method for main. Gets the rsID query from the user, calls binarySearch() above, and
     * prints to console any results given.
     * 
     * @param myGenome ArrayList of type <SNP>
     */
    public static void querySearch(ArrayList<SNP> myGenome) throws InputMismatchException {
        Scanner scnr2 = new Scanner(System.in);
        System.out.print("Enter your Rapid Stain Query (ex. 1863800): rs");
        int rsQuery = scnr2.nextInt();
        int result = binarySearch(myGenome, rsQuery, 0, myGenome.size() - 1);
        if (result == -1) {
            System.out.println("Not Found!");
        } else {
            System.out.println(myGenome.get(result).toString());
        }
    }

    // Main entry point of program
    public static void main(String[] args) {
        try {
            // Get the filename from the user
            Scanner scnr = new Scanner(System.in);
            System.out.print("Enter your genome's filename (ex. AncestryDNA.txt): ");
            String fileName = scnr.nextLine();

            //Reads in the user's file, uses makeList() defined above
            ArrayList<SNP> myGenome = makeList(new File(fileName));
            System.out.println("File Read.");

            // Sorts the user's genome using Collections to be able to use binarySearch
            System.out.print("Sorting your Genome...");
            Collections.sort(myGenome);
            System.out.println("Done.\n");

            // while loop that keeps the user in a permanent loop until exit is chosen, will reprompt if invalid input
            boolean exit = false;
            String userInput = "";
            System.out.println("Welcome to Genome Buddy.");
            while (!exit) {
                System.out.println("\n(a) Full Listing    (b) DIY Search");
                System.out.println("(c) AutoDoc         (d) Exit");
                System.out.print("Choose an option: ");
                userInput = scnr.nextLine().toLowerCase();
                System.out.println();
                switch (userInput) {
                    case "a":
                        printGenome(myGenome);
                        break;
                    case "b":
                        querySearch(myGenome);
                        break;
                    case "c":
                        System.out.println("AutoDoctor Selected. Scanning your genome for harmful mutations...");
                        AutoDoctor barry = new AutoDoctor(myGenome);
                        barry.autopsy();
                        break;
                    case "d":
                        exit = true;
                        break;
                    default:
                        System.out.println("\nNot a valid input. Enter a letter (a-d)");
                        break;
                }
            }
        } // Catch blocks to gracefully exit from checked errors.
        catch (FileNotFoundException e1) {
            System.out.println("File not found.");
        }
        catch (InputMismatchException e2) {
            System.out.println("Not a proper input! Did you enter an integer?");
        }
        catch (Exception e3) {
            e3.getMessage();
        }
    }
}
