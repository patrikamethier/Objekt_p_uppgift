/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_uppgift.Objekt_p_uppgift;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*; 
import javax.swing.tree.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Collection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.awt.Color; 
import static java.lang.Integer.parseInt;
/**
 *
 * @author patrikamethier
 */
public class Server extends JFrame {
    
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    //private ObjectOutputStream output2;
   // private ObjectInputStream input2;
    private ServerSocket server;
    private Socket connection; 
    public JPanel pattesPanel;
    public String whatjustwrote;
    public String serverName; 
    public String outhexColor; 
    public String inhexColor; 
    public String normalColor; 
    public String xmlString;
    public Color chatwindowcolor; 
    
   // private Socket connection2; 
    
    //constructor
    public Server(){
        super("Server");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener(){
                   public void actionPerformed(ActionEvent event){
                       try {
                           sendMessage(createXMLString(event.getActionCommand()));
                           //whatjustwrote = event.getActionCommand();
                           //createXMLString(whatjustwrote);
                       } catch (Exception ex) {
                           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       
                  
                       
                       userText.setText("");
                   }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300, 150);
        setVisible(true);
    }
    
    
    //set up and run the server
    public void startRunning(int port1){
        try{
            server = new ServerSocket(port1,100);  
            while(true){
                try{
                    //connect and have conversation
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                }catch(EOFException eofException){
                    showMessage("\n Server ended the Connection!");
                }finally{
                    closeCrap();
                }
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
            
        }
    }
    
    //wait for a connection, then display connection info
    private void waitForConnection() throws IOException{           
        showMessage(" Waiting for someone to connect... \n");
        connection = server.accept();
        showMessage(" Now connected to" + connection.getInetAddress().getHostName());
        //connection2 = server.accept();
       // showMessage(" Now connected to" + connection2.getInetAddress().getHostName());
    }
    
    //get stream to send and recieve data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage( "\n Streams are now set up! \n");
        //input2 = new ObjectInputStream(connection2.getInputStream());
       // showMessage( "\n Streams are now set up! \n");
        
    }
    
    //during the chat conversation
    private void whileChatting() throws IOException{
        String message = "<message name=" + "\"" + serverName + "\"" + "><text>You are now connected!</text></message>";
        try { 
            sendMessage(message);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        abletoType(true);
        
        do{
            try{
                message = (String) input.readObject();
                //Parse from XML 
                SharedMethods stringtoxml = new SharedMethods(); 
                String[] temp = stringtoxml.loadXMLFromString(message); 
                message = temp[0];
                showMessage("\n" + message);
                //message = (String) input2.readObject();
                //showMessage("\n" + message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n idk wtf that user sent!");
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(!message.equals("CLIENT - END"));
    }
                        
               
    //close streams and sockets after you are done chatting
    private void closeCrap(){
        showMessage("\n Closing connection... \n");
        abletoType(false);
        try{
            output.close();
            input.close();
            connection.close();  
        }catch(IOException ioException){
            ioException.printStackTrace();
        }        
    }
    
    //send a message to client
    private void sendMessage(String message) throws Exception{
        try{
            //output.writeObject("SERVER - " + message);
            output.writeObject(message);
            output.flush();
            //showMessage("\n SERVER - " + message);
            SharedMethods stringtoxml = new SharedMethods(); 
            String[] temp = stringtoxml.loadXMLFromString(message); 
            message = temp[0]; 
            SharedMethods hextorgb = new SharedMethods();
            int[] rgbnumbers = hextorgb.hextoRGB(outhexColor);
            chatwindowcolor = new Color(rgbnumbers[0], rgbnumbers[1], rgbnumbers[2]);
            chatWindow.setForeground(chatwindowcolor);
            showMessage("\n" + message); 
        }catch(IOException ioException){
            chatWindow.append("\n ERROR: DUDE I CANT SEND THAT MESSAGE");
        }
    }
    
    //updates chatWindow
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        chatWindow.append(text);
                    }
                }
        );
    }
    
    //let the user type stuff into their box
    private void abletoType(final boolean tof){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userText.setEditable(tof);
                }
            }  
        );
    }
    
    public String createXMLString(String inText){
        xmlString= "<message name="+ "\"" + serverName + "\"" + "><text color=" + "\""+ outhexColor + "\"" + ">"+inText+"</text></message>";
        System.out.println(xmlString);
        return xmlString; 
    }
    
}
        
   

