/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_uppgift.Objekt_p_uppgift;

import java.io.IOException;
import java.io.StringReader;
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author patrikamethier
 */
public class SharedMethods {
    public static int[] hextoRGB(String input){
        int[] result = new int[3];
        result[0]= parseInt((cutHash(input)).substring(0,2),16);
        result[1] = parseInt((cutHash(input)).substring(2,4),16);
        result[2] = parseInt((cutHash(input)).substring(4,6),16); 
        return result;  
    }
    public static String cutHash(String input){
            return input.substring(1);  
    }
    
    public static String[] loadXMLFromString(String xml) throws Exception{
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(xml));

    Document doc = db.parse(is);
    NodeList nodes = doc.getElementsByTagName("message");
    Element mess = (Element) nodes.item(0);
    String name = mess.getAttribute("name");
    Node text = mess.getFirstChild();
    Element textel = (Element) text; 
    String textattr = textel.getAttribute("color"); 
   String[] result = new String[2];
   result[0] = name +": " + text.getTextContent();
   result[1] = textattr; 
   //System.out.println("server just loaded a textattr: "+ textattr); 
   return result; 
}
    
     
    
}
