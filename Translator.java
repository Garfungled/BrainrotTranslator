import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class Translator {
   public static void main(String[] args) throws IOException{
       DataManager.updateValues();


       // User input
       // Scanner consoleScanner = new Scanner(System.in);
       // String consoleInput = consoleScanner.nextLine();


       // Scanner valuesScanner = new Scanner(new File(DataManager.valuesFileName));


       // String values = valuesScanner.nextLine(); // Fake values for now, in the future use getAllValues


       // int currentCommaIndex = values.indexOf(',');
       // int lastCommaIndex = 0;
       // while(currentCommaIndex != -1) {
       //     String currentValue = values.substring(lastCommaIndex, currentCommaIndex);
       //     currentValue =  currentValue.replace(",", "");


       //     // Logic for comparing, replacing, etc.
       //     System.out.println(currentValue);


       //     lastCommaIndex = currentCommaIndex;
       //     currentCommaIndex = values.indexOf(',', currentCommaIndex + 1);
       // }
   }
}
