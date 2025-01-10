import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Translator {
    public static void main(String[] args) throws IOException{
        Scanner consoleScanner;
        Scanner valuesScanner;

        boolean running = true;
        boolean debug = true;

        while(running) {
            // User input
            consoleScanner = new Scanner(System.in);
            valuesScanner = new Scanner(new File(DataManager.valuesFileName));

            System.out.println("Enter any type or amount of text that you'd like to be brainrotted: ");
            String consoleInput = consoleScanner.nextLine().toLowerCase();
            String values = valuesScanner.nextLine();
            String result = consoleInput;

            // Debug Info
            String wordsChanged = "";
            int numChanges = -1;

            int currentCommaIndex = values.indexOf(',');
            int lastCommaIndex = 0;
            while(currentCommaIndex != -1) {
                String currentValue = values.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

                // Logic for comparing, replacing, etc.
                if (consoleInput.contains(currentValue)) {
                    String keyMatches = DataManager.getKeysFromValue(currentValue);
                    String weightMatches = DataManager.getWeightsFromValue(currentValue);
                    result = result.replace(currentValue, weightedStringChoice(keyMatches, weightMatches));

                    // Debug Info
                    numChanges++;
                    wordsChanged += "\t" + currentValue + " -> " + result + "\n";
                }

                lastCommaIndex = currentCommaIndex;
                currentCommaIndex = values.indexOf(',', currentCommaIndex + 1);
            }

            System.out.println("Brainrot:\n" + result);

            if (debug) {
                System.out.println("Debug info");
                System.out.println("\tNumber of Words changed: " + numChanges);
                System.out.println("\tProcess: \n" + wordsChanged);
            }

            System.out.println("\nWould you like to enter another string? (y/n): ");
            String response = consoleScanner.nextLine().toLowerCase();
            while(!(response.equals("y") || response.equals("n"))) {
                System.out.println("\nPlease enter (y) or (n)");
                response = consoleScanner.nextLine().toLowerCase();
            }

            running = response.equals("y");
            System.out.println();
        }
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