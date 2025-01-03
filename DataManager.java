import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class DataManager {
   public static String csvFileName = "CSV/main.csv";
   public static String valuesFileName = "CSV/values.txt";


   public static void updateValues() throws IOException {
       FileWriter fileWriter = new FileWriter(valuesFileName);
       fileWriter.write(getAllValues());
       fileWriter.close();
   }


   // Returns all the values from the CSV in one long string (with no duplicates)
   // output: "value1,value2,value3,..."
   public static String getAllValues() {
       String total = "";
       try {
           Scanner csvScanner = new Scanner(new File(csvFileName));
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


   // input: "value1,value2,value3,...", output is the same
   public static String clearDuplicates(String n) {
       String result = "";


       int currentCommaIndex = n.indexOf(',');
       int lastCommaIndex = 0;
       while(currentCommaIndex != -1) {
           String currentValue = n.substring(lastCommaIndex, currentCommaIndex);
           currentValue =  currentValue.replace(",", "");


           if (!result.contains(currentValue)) {
               result += currentValue + ",";
           }


           lastCommaIndex = currentCommaIndex;
           currentCommaIndex = n.indexOf(',', currentCommaIndex + 1);
       }


       return result;
   }
}
