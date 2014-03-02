/**
 * Ten przykład pokazuje jak odebrać paragon fiskalny z serwera. W ten sposób
 * paragon do wydruku pobiera applet.
 */
package name.prokop.bart.fps.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import name.prokop.bart.fps.datamodel.Slip;
import org.json.JSONObject;

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

        StringBuilder sb = new StringBuilder();
        System.out.println("response code: " + conn.getResponseCode());
        for (Map.Entry<String, List<String>> e : conn.getHeaderFields().entrySet()) {
            System.out.println(e);
        }
        System.out.println("Response from the server:");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                sb.append(inputLine).append('\n');
            }
        }

        JSONObject jsono = new JSONObject(sb.toString());
        System.out.println("Deserializowany JSON: " + jsono.toString());
        Slip slip = new Slip(jsono);
        System.out.println("Nr paragonu: "+slip.getReference());
    }
}
