
package com.beydest;
import java.io.File;
import org.json.*;
import org.apache.commons.io.FileUtils;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class BeyDestDataReader {

    public static void main(String[] args) {
        try {

            URL url = new URL("https://api.thingspeak.com/channels/515321/feeds.json?api_key=TWMWWUA36Y7UGW35&results=9999999999999999");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());
                try  {
                    //Write all the JSON data into a string using a scanner
                    while (scanner.hasNext()) {
                        String x = scanner.nextLine();
                        inline += x;
                    }
                    System.out.println(inline);
                    JSONObject output = new JSONObject(inline);
                    JSONArray docs = output.getJSONArray("feeds");

                    File file = new File("data/file.csv");
                    String csv = CDL.toString(docs);
                    FileUtils.writeStringToFile(file, csv);
                } catch(Exception e) {
                    System.out.println("dd");
                    System.out.println(""+e.getMessage());
                }
                //Close the scanner
                scanner.close();
                System.out.println(inline);            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}