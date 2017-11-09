import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WebService {
    public static void main(String[] args) throws IOException {
        String url = "http://services.faa.gov/airport/status/BOS/?format=application/xml";
        // Create a URL and open a connection
        URL airportURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) airportURL.openConnection();

        // Set the HTTP Request type method to GET (Default: GET)
        con.setRequestMethod("GET");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);

        // Created a BufferedReader to read the contents of the request.
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        DOMParser parser = new DOMParser();

        while ((inputLine = in.readLine()) != null) {

            response.append((inputLine + "\n"));
        }
        String out = response.toString();
        //URI myURI = new URI(out);

        try {
            DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
            DocumentBuilder bd = db.newDocumentBuilder();
            Document doc = bd.parse(new InputSource(new StringReader(response.toString())));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("AirportStatus");

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            System.out.println("----------------------------");


                Node nNode = nList.item(0);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Delay : " + eElement.getElementsByTagName("Delay").item(0).getTextContent());
                    System.out.println("IATA : " + eElement.getElementsByTagName("IATA").item(0).getTextContent());
                    System.out.println("State : " + eElement.getElementsByTagName("State").item(0).getTextContent());
                    System.out.println("Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());
                    System.out.println("Weather : " + eElement.getElementsByTagName("Weather").item(0).getTextContent());
                    System.out.println("ICAO : " + eElement.getElementsByTagName("ICAO").item(0).getTextContent());
                    System.out.println("City : " + eElement.getElementsByTagName("City").item(0).getTextContent());
                    System.out.println("Status : " + eElement.getElementsByTagName("Status").item(0).getTextContent());

                }


        }
        catch (Exception e)
        {
            e.getMessage();
        }

        // MAKE SURE TO CLOSE YOUR CONNECTION!
        in.close();

        // response is the contents of the XML
        //System.out.println(response.toString());
    }
}