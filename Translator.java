import java.util.Scanner;
import java.io.File;
import java.io.IOException;


    public class Translator {
    public static void main(String[] args) throws IOException{
        // User input
        System.out.println("Enter a string you want to be brainrotted: ");
        Scanner consoleScanner = new Scanner(System.in);
        String consoleInput = consoleScanner.nextLine();
        consoleScanner.close();

        Scanner valuesScanner = new Scanner(new File(DataManager.valuesFileName));
        String values = valuesScanner.nextLine();
        valuesScanner.close();

        String result = consoleInput;

        int currentCommaIndex = values.indexOf(',');
        int lastCommaIndex = 0;
        while(currentCommaIndex != -1) {
            String currentValue = values.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

            // Logic for comparing, replacing, etc.
            if (consoleInput.contains(currentValue)) {
                String keyMatches = DataManager.getKeysFromValue(currentValue);
                String weightMatches = DataManager.getWeightsFromValue(currentValue);
                result = result.replace(currentValue, weightedStringChoice(keyMatches, weightMatches));
            }

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = values.indexOf(',', currentCommaIndex + 1);
        }

        System.out.println(result);
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
