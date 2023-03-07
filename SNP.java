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
 * The SNP class (standing for Single Nucleotide Pair) is a comparable interface developed for storing
 * the variables of each row of data in the genome file. A constructor initializes all of the variables
 * read in from the GenomeBuddy class, and provides the standard getter methods for every attribute. Note
 * toString() is overridden to imitate how the row appears in the txt file, and compareTo is modified so
 * an ArrayList<SNP> when put into Collections.sort() will sort the whole genome by the rsID. This allows 
 * binarySearch() to be used for queries.
 *
 * @author Evan Duffield
 * @version 12/8/2022
 */

public class SNP implements Comparable<SNP> {
    // Class wide variables storing the row data
    private final int rsID;
    private final String rsIDString;
    private final int chromosome;
    private final int position;
    private final String allele1;
    private final String allele2;

    /* Constructor method. Recieves the row data and assigns it to its proper
     * class variable.
     * 
     * @param rsID first column of the textfile
     * @param chromosome second column of the textfile
     * @param position third column of the textfile
     * @param allele1 fourth column of the textfile
     * @param allele2 fifth column of the textfile
     * 
     */
    public SNP(String rsID, int chromosome, int position, String allele1, String allele2) {
        this.rsID = Integer.parseInt(rsID.substring(2));
        this.rsIDString = rsID;
        this.chromosome = chromosome;
        this.position = position;
        this.allele1 = allele1;
        this.allele2 = allele2;
    }

    //Standard getter methods for every class variable.
    public int getRsID() { return rsID; }

    public String getRsIDString() { return rsIDString; }

    public int getChromosome() { return chromosome; }

    public int getPosition() { return position; }

    public String getAllele1() { return allele1; }

    public String getAllele2() { return allele2; }

    // toString() overridden to emulate how the text was formatted in the txt file
    @Override
    public String toString() { return String.format("rs%-9d %2d  %9d\t%s\t%s", rsID, chromosome, position, allele1, allele2); }

    // compareTo overridden to be able to sort the user's genome by the rsID
    @Override
    public int compareTo(SNP otherSNP) {
        if (rsID < otherSNP.getRsID()) {
            return -1;
        }
        else if (rsID > otherSNP.getRsID()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
