/**
 * Ten przykład pokazuje jak wysłać paragon fiskalny do serwera, skąd zostanie
 * oddrukowany
 */
package name.prokop.bart.fps.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import name.prokop.bart.fps.datamodel.CustomerSamples;
import name.prokop.bart.fps.datamodel.Slip;

public class Example1 {

    public static void main(String... args) throws Exception {
        String urlString = DemoConstants.URL_BASE;
        urlString = urlString.concat("/postDocument");
        
        System.out.println("URL: " + urlString);
        URL url = new URL(urlString);

        URLConnection urlConnection = url.openConnection();
        System.out.println("urlConnection type: " + urlConnection.getClass());
        HttpsURLConnection conn = (HttpsURLConnection) urlConnection;
        conn.setRequestProperty(DemoConstants.API_KEY_HEADER, DemoConstants.API_KEY);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        try (OutputStream outputStream = conn.getOutputStream()) {
            Slip slip = CustomerSamples.example1();
            String slipAsJSON = slip.toJSONObject().toString(3);
            System.out.println("To be send:\n" + slipAsJSON);
            System.out.println("-----------------------------");
            outputStream.write(slipAsJSON.getBytes("UTF-8"));
        }

        System.out.println("response code: " + conn.getResponseCode());
        System.out.println("Response from the server:");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        }
    }
}
