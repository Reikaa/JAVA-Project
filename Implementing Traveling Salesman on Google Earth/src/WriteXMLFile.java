import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
/**
 * @author Crunchify.com
 */
 
public class WriteXMLFile{
 
    public static void main(String[] args) throws IOException {
    	
		System.out.println("Enter start index:");
		Scanner input = new Scanner(System.in);
		int start = input.nextInt();

		System.out.println("Enter end index:");
		input = new Scanner(System.in);
		int end = input.nextInt();

		Graph graph = new Graph(start, end);
		String tree = graph.Prim(0);
		Graph graph2 = new Graph(start, end);
		String bruteForce = graph2.bruteForce();
    	
    	
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElementNS("http://earth.google.com/kml/2.2", "kml");
            Attr xmlns = doc.createAttribute("xmlns");
            xmlns.setValue("http://earth.google.com/kml/2.2");
            mainRootElement.setAttributeNode(xmlns);
            doc.appendChild(mainRootElement);
            
            /**
    		 * Document has:
    		 * name
    		 * description
    		 * Style
    		 * 		LineStyle
    		 * 			color
    		 * 			width
    		 * Placemark
    		 * 		name
    		 * 		description
    		 * 		styleUrl
    		 * 		LineString
    		 * 			tessellate
    		 * 			coordinates
    		 */
    		
    		// build Document
            Element Document = doc.createElement("Document");
    		mainRootElement.appendChild(Document);
    		
    		//build name, description, style, and placemark, insert it to Document
    		Element name = doc.createElement("name");
    		Element description = doc.createElement("description");
    		
    		// append them to Document
    		Document.appendChild(name);
    		Document.appendChild(description);
    		
    		//create value for name
    		name.appendChild(doc.createTextNode("Pittsburgh TSP"));
    		//create value for description
    		description.appendChild(doc.createTextNode("TSP on Crime"));
    		Document.appendChild(setStyle(doc, "style6", "73FF0000", "5"));
    		Document.appendChild(setStyle(doc, "style5", "507800F0", "5"));
    		Document.appendChild(setPlacemark(doc, "TSP Path", "TSP Path", "#style6", graph.coordinates(tree)));
    		Document.appendChild(setPlacemark(doc, "Optimal Path", "Optimal Path", "#style5", graph.coordinates(bruteForce)));
    		

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);
 
            System.out.println("\nXML DOM Created Successfully..");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Node setStyle(Document doc, String id, String colorVal, String widthVal)
    {
    	Element Style = doc.createElement("Style");
    	Style.setAttribute("id", id);
    	
    	Element LineStyle = doc.createElement("LineStyle");
    	Element color = doc.createElement("color");
		Element width = doc.createElement("width");
		//setvalue for color and width
		color.appendChild(doc.createTextNode(colorVal));
		width.appendChild(doc.createTextNode(widthVal));
		
		//append those to Linestyle
		LineStyle.appendChild(color);
		LineStyle.appendChild(width);
		// and finally append Linestyle to Style
		Style.appendChild(LineStyle);
		
		return Style;
		
    	
    }
    
    private static Node setPlacemark(Document doc, String name, String desc, String styleUrl, ArrayList<String> coord)
    {
    	Element Placemark = doc.createElement("Placemark");
    	Placemark.appendChild(getElement(doc, "name", name));
    	Placemark.appendChild(getElement(doc, "description", desc));
    	Placemark.appendChild(getElement(doc, "styleUrl", styleUrl));
    	Placemark.appendChild(setLineString(doc, "1", coord));
    	
    	return Placemark;
    	
    }
    
    private static Node setLineString(Document doc, String tessel, ArrayList<String> coord)
    {
    	Element LineString = doc.createElement("LineString");
    	LineString.appendChild(getElement(doc, "tessellate", tessel));
    	
    	Element coordinates = doc.createElement("coordinates");
    	for(String u: coord)
    	{
    		coordinates.appendChild(doc.createTextNode("\n"));
    		coordinates.appendChild(doc.createTextNode(u));
    	}
    	
    	coordinates.appendChild(doc.createTextNode("\n"));
    	
    	LineString.appendChild(coordinates);
    	return LineString;
    }

    private static Node getElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}