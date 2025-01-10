import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class DataManager {
    public static String csvFileName = "Data/main.txt";
    public static String valuesFileName = "Data/values.txt";
    public static String processedFileName = "Data/brainrot.txt";


    public static void updateValues() throws IOException {
        FileWriter fileWriter = new FileWriter(valuesFileName);
        fileWriter.write(getAllValues());
        fileWriter.close();
    }

    public static String getKeysFromValue(String value) {
        String total = "";
        try {
            Scanner csvScanner = new Scanner(new File(processedFileName));
            while(csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                if (listContains(currentLine, value)) {
                    int indexOfFirstComma = currentLine.indexOf(",");
                    int indexOfSecondComma = currentLine.indexOf(",", indexOfFirstComma + 1);
                    total += currentLine.substring(indexOfFirstComma + 1, indexOfSecondComma) + ",";
                }
            }
            csvScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            System.out.println(e);
        }

        return total;
    }

    public static String getWeightsFromValue(String value) {
        String total = "";
        try {
            Scanner csvScanner = new Scanner(new File(processedFileName));
            while(csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                if (listContains(currentLine, value)) {
                    total += currentLine.substring(0, currentLine.indexOf(",")) + ",";
                }
            }
            csvScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            System.out.println(e);
        }

        return total;
    }

    // Returns all the values from the CSV in one long string (with no duplicates)
    // output: "value1,value2,value3,..."
    public static String getAllValues() {
        String total = "";
        try {
            Scanner csvScanner = new Scanner(new File(processedFileName));
            while(csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                total += currentLine.substring(currentLine.indexOf(',', currentLine.indexOf(',') + 1) + 1) + ",";
            }
            csvScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            System.out.println(e);
        }
      
        return clearDuplicates(total);
    }

    public static String getAllKeys() {
        String total = "";
        try {
            Scanner csvScanner = new Scanner(new File(processedFileName));
            while(csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                total += currentLine.substring(currentLine.indexOf(','), currentLine.indexOf(',', currentLine.indexOf(',') + 1) + 1) + ",";
            }
            csvScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            System.out.println(e);
        }
      
        return total;
    }

    public static String getAllWeights() {
        String total = "";
        try {
            Scanner csvScanner = new Scanner(new File(processedFileName));
            while(csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                total += currentLine.substring(0, currentLine.indexOf(',')) + ",";
            }
            csvScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            System.out.println(e);
        }
      
        return total;
    }


   // input: "value1,value2,value3,...", output is the same
    public static String clearDuplicates(String n) {
        String result = "";

        int currentCommaIndex = n.indexOf(',');
        int lastCommaIndex = 0;
        while(currentCommaIndex != -1) {
            String currentValue = n.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

            if (!listContains(result, currentValue)) {
                result += currentValue + ",";
            }

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = n.indexOf(',', currentCommaIndex + 1);
        }

        return result;
    }

    public static int getWeightSum(String weights) {
        int totalWeights = 0;

        int currentCommaIndex = weights.indexOf(',');
        int lastCommaIndex = 0;
        while(currentCommaIndex != -1) {
            String currentWeight = weights.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

            totalWeights += Integer.parseInt(currentWeight);

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = weights.indexOf(',', currentCommaIndex + 1);
        }

        return totalWeights;
    }

    // Takes a normal sentence and turns it into a string list: "word1,word2,word3,...,"
    // e.x. n = "Hi my name is soandso." -> "hi,my,name,is,soandso,"
    public static String stringToList(String n) {
        return n.toLowerCase().replace(",", "").replace(' ', ',') + ',';
    }

    // Taken from pickcode for consumerReview
    public static String removePunctuation(String n) {
        while(n.length() > 0 && !Character.isAlphabetic(n.charAt(0))) {
            n = n.substring(1);
        }

        while(n.length() > 0 && !Character.isAlphabetic(n.charAt(n.length()-1))) {
            n = n.substring(0, n.length()-1);
        }
        
        return n;
    }
    
    // Given a string list n with delimiters, find the item at index i
    // e.x., n = "item1,item2,item3," if ',' is the delimiter, item2 is at index 1
    public static String itemAtIndex(String n, int index) {
        int currCommaIndex = n.indexOf(',');
        int lastCommaIndex = 0;
        for(int i = 0; i < index; i++) {
            lastCommaIndex = currCommaIndex;
            currCommaIndex = n.indexOf(',', currCommaIndex + 1);
        }
        return n.substring(lastCommaIndex, currCommaIndex).replace(",", "");
    }

    // Since we don't want to use .contains
    // e.x. "bro" is in "brought" but "bro" and "brought" are not the same
    public static boolean listContains(String n, String other) {
        int currentCommaIndex = n.indexOf(',');
        int lastCommaIndex = 0;
        while(currentCommaIndex != -1) {
            String currentItem = n.substring(lastCommaIndex, currentCommaIndex).replace(",", "");

            if (currentItem.equals(other)) {
                return true;
            }

            lastCommaIndex = currentCommaIndex;
            currentCommaIndex = n.indexOf(',', currentCommaIndex + 1);
        }

        return false;
    }

    public static void updateFile() {
        String fileURL = "https://docs.google.com/spreadsheets/d/1wXt3BZlrV8dCR_GeXwsCgYEyPFZaEsNBSJPdGWTs8Rg/gviz/tq?tqx=out:csv&sheet=Slang-Term_Common-Substitute";
        String saveFileName = "main.txt";

        String packagePath = System.getProperty("user.dir"); // Replace with your package path
        String saveFilePath = packagePath + "/" + saveFileName;

        try {
            File existingFile = new File(saveFilePath);
            if (existingFile.exists()) {
                if (existingFile.delete()) {
                    System.out.println("Deleted existing file: " + saveFilePath);
                } else {
                    System.out.println("Failed to delete existing file: " + saveFilePath);
                    return;
                }
            }

            @SuppressWarnings("deprecation")
            URL url = new URL(fileURL);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            // check http response code
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                // buffer for reading and writing
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // close streams
                outputStream.close();
                inputStream.close();

                System.out.println("Downloaded new file: " + saveFilePath);
            } else {
                System.out.println("No file to download. Server replied with response code: " + responseCode);
            }

            // disconnect
            httpConnection.disconnect();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void processFile() {
        try {

            Scanner csvScanner = new Scanner(new File(csvFileName));

            FileWriter txtWriter = new FileWriter(processedFileName);

            while (csvScanner.hasNextLine()) {
                String currentLine = csvScanner.nextLine();
                currentLine = currentLine.replaceAll("\"\",", "");
                currentLine = currentLine.replaceAll("\"", "");
                currentLine = currentLine.replaceAll(", ,", ",");
                currentLine = currentLine.toLowerCase();
                txtWriter.write(currentLine);
                if (csvScanner.hasNextLine()) {
                    txtWriter.write(System.lineSeparator());
                }
            }

            csvScanner.close();
            txtWriter.close();

            System.out.println("Processing complete. Output saved to: " + processedFileName);
        } catch (IOException e) {
            System.out.println("Error processing CSV");
        }
    }

    public static void main(String[] args) throws IOException {
        updateFile();
        processFile();
        updateValues();
    }
}
