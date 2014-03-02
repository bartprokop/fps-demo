/**
 * Ten przykład pokazuje jak odebrać paragon fiskalny z serwera. W ten sposób
 * paragon do wydruku pobiera applet.
 */
package name.prokop.bart.fps.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ExampleReadSlip {

    public static void main(String... args) throws Exception {
        String urlString = DemoConstants.URL_BASE;
        urlString = urlString.concat("/getDocument");
        urlString = urlString.concat("?documentId=");
        // this value we got when we were sending document to server
        urlString = urlString.concat("3cfe7238-f86a-4912-9049-66f1e256a317");

        System.out.println("URL: " + urlString);
        URL url = new URL(urlString);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestProperty(DemoConstants.API_KEY_HEADER, DemoConstants.API_KEY);
        conn.setRequestMethod("GET");

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
