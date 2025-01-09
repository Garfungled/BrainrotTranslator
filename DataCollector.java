//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class DataCollector {
//    public static void updateFile () {
//        String fileURL = "https://docs.google.com/spreadsheets/d/1wXt3BZlrV8dCR_GeXwsCgYEyPFZaEsNBSJPdGWTs8Rg/gviz/tq?tqx=out:csv&sheet=Slang-Term_Common-Substitute";
//        String saveFileName = "main.csv";
//
//        String packagePath = System.getProperty("user.dir"); // Replace with your package path
//        String saveFilePath = packagePath + "/" + saveFileName;
//
//        try {
//            File existingFile = new File(saveFilePath);
//            if (existingFile.exists()) {
//                if (existingFile.delete()) {
//                    System.out.println("Deleted existing file: " + saveFilePath);
//                } else {
//                    System.out.println("Failed to delete existing file: " + saveFilePath);
//                    return;
//                }
//            }
//
//            URL url = new URL(fileURL);
//            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
//
//            // check http response code
//            int responseCode = httpConnection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
//                FileOutputStream outputStream = new FileOutputStream(saveFilePath);
//
//                // buffer for reading and writing
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//
//                // close streams
//                outputStream.close();
//                inputStream.close();
//
//                System.out.println("Downloaded new file: " + saveFilePath);
//            } else {
//                System.out.println("No file to download. Server replied with response code: " + responseCode);
//            }
//
//            // disconnect
//            httpConnection.disconnect();
//        } catch (IOException e) {
//            System.out.println("An error occurred: " + e.getMessage());
//        }
//    }
//}