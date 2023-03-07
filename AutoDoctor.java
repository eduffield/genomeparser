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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * The AutoDoctor class is responsible for reading and handling the genotypes.json file,
 * and returning to console any negative mutations that were found in the genome ArrayList it gets
 * passed in its' constructor. The constructor will read in both the user's genome and the json, and
 * autopsy() actually iterates through and checks for mutations.
 *
 * @author Evan Duffield
 * @version 12/8/2022
 */

public class AutoDoctor {
    // Class wide variables storing the json dictionary and the userGenome passed into its constructor.
    private JSONObject jsonFile;
    private ArrayList<SNP> userGenome;

    /* AutoDoctor class constructor. Takes in ArrayList of the user's genome, and attempts to read in the
     * json dictionary.
     * 
     * @param userGenome ArrayList of the SNP class collection, storing the user's genome to read and analyze.
     */
    public AutoDoctor(ArrayList<SNP> userGenome) {
        this.userGenome = userGenome;
        try {
            jsonFile = (JSONObject) new JSONParser().parse(new FileReader("genotypes.json"));
        } catch (FileNotFoundException e1) {
            System.out.println("Cannot find the JSON Dictionary! Exiting...");
        } catch (IOException e2) {
            System.out.println("Cannot read the JSON Dictionary! Exiting...");
        } catch (ParseException e3) {
            System.out.println("Cannot parse the JSON Dictionary! (Is it a json?) Exiting...");
        }

    }

    /* autopsy() loops through the ArrayList of the user's genome, seeing if any of the genes have an
     * entry in the json dictionary. It then proceeds to enter further into the nested json and will return
     * any mutations listed as "Bad" and what the diagnosis is. Note the several nests of JSONObjects, as simple-json.jar
     * doesnt support nested json files.
     * 
     */
    public void autopsy() throws Exception {
        // for every gene in the user's genome
        for (SNP line : userGenome) {
            // Gets the keys from the user's genome in string form
            String queryKey = line.getRsIDString();
            String bases = String.format("%s;%s", line.getAllele1(), line.getAllele2());
            if (jsonFile.containsKey(queryKey)) {
                JSONObject rsID = (JSONObject) jsonFile.get(queryKey);
                String sanitizer = rsID.toJSONString();
                
                // Only if an entry has a bad prognosis, has an entry for the user's specific mutation, and has a diagnosis ready
                if (sanitizer.contains("Bad") && sanitizer.contains(bases) && sanitizer.contains("\"r\":\"")) {
                    if (rsID.containsKey(bases)) {
                        JSONObject diagnosis = (JSONObject) rsID.get(bases);
                        if (diagnosis.containsKey("r")) {
                            String howBad = (String) diagnosis.get("r");
                            if (howBad.equals("Bad")) {
                                
                                // Prints the diagnosis to console alongside where it was found in the genome
                                String result = (String) diagnosis.get("s");
                                String outputFinal = String.format("%-20s\t%-40s", line.toString(), result);
                                System.out.println(outputFinal);
                            }
                        }
                    }
                }
            }
        }
    }

}
