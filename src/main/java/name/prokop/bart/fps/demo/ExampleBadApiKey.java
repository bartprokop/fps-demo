/**
 * Ten przykład pokazuje co się stanie gdy wyślemy błedne Api Key
 */
package name.prokop.bart.fps.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class ExampleBadApiKey {

    public static void main(String... args) throws Exception {
        String urlString = DemoConstants.URL_BASE;
        urlString = urlString.concat("/postDocument");

        System.out.println("URL: " + urlString);
        URL url = new URL(urlString);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestProperty(DemoConstants.API_KEY_HEADER, "We try to guess it...");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        try (OutputStream outputStream = conn.getOutputStream()) {
            outputStream.write("It doesnt matter what we send....".getBytes("UTF-8"));
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
