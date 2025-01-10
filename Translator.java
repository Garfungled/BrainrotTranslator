import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Translator {
    public static void main(String[] args) throws IOException{
        Scanner consoleScanner = null;
        Scanner valuesScanner = null;

        boolean running = true;
        boolean debug = true;

        while(running) {
            // User input
            consoleScanner = new Scanner(System.in);
            valuesScanner = new Scanner(new File(DataManager.valuesFileName));

            System.out.println("Enter any text that you'd like to be brainrotted: ");
            String consoleInput = consoleScanner.nextLine().toLowerCase();
            String values = valuesScanner.nextLine();
            String result = consoleInput;

            // Debug Info
            String wordsChanged = "";
            int numChanges = 0;

            // Translation
            int currentCommaIndex = values.indexOf(',');
            int lastCommaIndex = 0;
            while(currentCommaIndex != -1) {
                String currentValue = values.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

                // Logic for comparing, replacing, etc.
                if (consoleInput.contains(currentValue)) {
                    String keyMatches = DataManager.getKeysFromValue(currentValue);
                    String weightMatches = DataManager.getWeightsFromValue(currentValue);
                    String brainrot = weightedStringChoice(keyMatches, weightMatches);
                    String newString = smartReplace(result, currentValue, brainrot);

                    // Debug Info
                    if (!newString.equals(result)) {
                        numChanges++;
                        wordsChanged += "\t" + currentValue + " -> " + newString + "\n";
                    }

                    result = newString;
                }

                lastCommaIndex = currentCommaIndex;
                currentCommaIndex = values.indexOf(',', currentCommaIndex + 1);
            }

            // Final result
            System.out.println("\nBrainrot:\n" + result);
            if (debug) {
                System.out.println("\nDebug info");
                System.out.println("\tNumber of Words changed: " + numChanges);
                System.out.println("\tProcess: \n" + wordsChanged);
            }

            // Prompt for another translation
            System.out.println("\nWould you like to enter another string? (y/n): ");
            String response = consoleScanner.nextLine().toLowerCase();
            while(!(response.equals("y") || response.equals("n"))) {
                System.out.println("\nPlease enter (y) or (n)");
                response = consoleScanner.nextLine().toLowerCase();
            }

            running = response.equals("y");
            System.out.println();
        }

        // Prompt for contribution to dataset
        System.out.println("Would you like to contribute to this project? (y/n)");
        consoleScanner = new Scanner(System.in);
        String response = consoleScanner.nextLine().toLowerCase();
        while(!(response.equals("y") || response.equals("n"))) {
            System.out.println("\nPlease enter (y) or (n)");
            response = consoleScanner.nextLine().toLowerCase();
        }

        if (response.equals("y")) {
            System.out.println("\nIf you think we missed some brainrot terms or have some possible synonyms that could be replaced with brainrot terms, check out our crowd sourced google spreadsheet where you can freely contribute to our project. Here's how to do so (information is also on the README):");
            System.out.println("\t1. Go to https://docs.google.com/spreadsheets/d/1wXt3BZlrV8dCR_GeXwsCgYEyPFZaEsNBSJPdGWTs8Rg/edit?gid=453750728#gid=453750728");
            System.out.println("\t2. Scroll to the bottom and add a row");
            System.out.println("\t3. First column is for the weight (think of it as how often it should replace), second is for the brainrot term, and all other proceeding columns are for any synonyms (word replacements) you can think of for the brainrot term (see README for what should and shouldn't be added to the spreadsheet)");
            System.out.println("\t4. Go back to this folder, run \'java DataManager.java\' to locally update your dataset, and enjoy!");
            System.out.println("\nCheck out the github repo too!");
            System.out.println("\thttps://github.com/Garfungled/BrainrotTranslator");
        }

        consoleScanner.close();
        valuesScanner.close();
    }

    // Replace only whole words, i.e. not replacing "bro" in "brought"
    public static String smartReplace(String n, String target, String replacement) {
        n = DataManager.stringToList(n);
        String result = "";

        int currentCommaIndex = n.indexOf(',');
        int lastCommaIndex = 0;
        while(currentCommaIndex != -1) {
            String currentWord = n.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

            if (DataManager.removePunctuation(currentWord).equals(target)) {
                currentWord = replacement;
            }
            result += currentWord + " ";

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = n.indexOf(',', currentCommaIndex + 1);
        }

        return result;
    }

    // replaces all ending vowels with 'uzz'
    public static String huzzify(String n) {
        String vowels = "a,e,i,o,u,";
        if (vowels.contains(n.charAt(n.length() - 1) + "")) {
            n = n.substring(0, n.length() - 1) + "uzz";
        }

        return n;
    }

    // keys: "key1,key2,...,", weights: "weight1,weight2,...,"
    public static String weightedStringChoice(String keys, String weights) {
        int totalWeights = DataManager.getWeightSum(weights);
        int rng = (int)(Math.random() * totalWeights) + 1;

        int i = 0; // index used for both keys and weights since they're the same "size"
        int currentCommaIndex = weights.indexOf(',');
        int lastCommaIndex = 0;
        int tempWeightSum = 0;
        while(currentCommaIndex != -1) {
            String currentWeight = weights.substring(lastCommaIndex, currentCommaIndex).replace(",", "");
            int weight = Integer.parseInt(currentWeight);
            tempWeightSum += weight;

            if (rng <= tempWeightSum) {
                return DataManager.itemAtIndex(keys, i);
            }

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = weights.indexOf(',', currentCommaIndex + 1);
            i += 1;
        }

        return "";
    }
}