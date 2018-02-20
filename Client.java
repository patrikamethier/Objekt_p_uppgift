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
import java.io.StringReader;
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import static projekt_uppgift.Server.loadXMLFromString;


/**
 *
 * @author patrikamethier
 */
public class Client extends JFrame{
    
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;
    public String clientName; 
    public String xmlColor; 
    public String outhexColor;
    public String normalColor; 
    public String whatjustwrote; 
    public String xmlString; 
    public Color chatwindowcolor;
    
    
    //constructor
    public Client(int port, String host){
        super("Client");
        serverIP = host; 
        userText = new JTextField();
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
        //chatWindow.setForeground(Color.PINK); 
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 150);
        setVisible(true);
        
    }
    
    //connect to server
    public void startRunning(int port1){
        try{
            connectToServer(port1);
            setupStreams();
            whileChatting();
            
        }catch(EOFException eofException){
            showMessage("\n Client terminated connection");
            
        }catch(IOException ioException){
            ioException.printStackTrace();
            
        }finally{
            closeCrap();
        }
    }
    
    //connect to server
    private void connectToServer(int port1) throws IOException{
        showMessage("Attempting connection... \n");
        connection = new Socket(InetAddress.getByName(serverIP), port1);
        showMessage("Connected to:" + connection.getInetAddress().getHostName());
        
    }
    
    //set up streams to send and recieve messages
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage(" \n Dude your streams are now good to go! \n"); 
        
    }
    
    //while chatting with server
    private void whileChatting() throws IOException{
        String message = "<message name=" + "\"" + clientName + "\"" + "><text>You are now connected!</text></message>";
        try { 
            sendMessage(message);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ableToType(true);
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
        }while(!message.equals("SERVER - END"));
    }
    
    
    //close the streams and sockets
    private void closeCrap(){
        showMessage("\n closing crap down...");
        ableToType(false); 
        try{
            output.close();
            input.close();
            connection.close(); 
            
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    //send messages to server
    private void sendMessage(String message) throws Exception{
        try{
            //output.writeObject("CLIENT - " + message);
            output.writeObject(message);
            output.flush();
            //showMessage("\n CLIENT - " + message);
            SharedMethods stringtoxml = new SharedMethods(); 
            String[] temp = stringtoxml.loadXMLFromString(message);  
            message = temp[0]; 
            SharedMethods hextorgb = new SharedMethods();
            int[] rgbnumbers = hextorgb.hextoRGB(outhexColor);
            chatwindowcolor = new Color(rgbnumbers[0], rgbnumbers[1], rgbnumbers[2]);
            chatWindow.setForeground(chatwindowcolor);
            showMessage("\n" + message); 
            
        }catch(IOException ioException){
            chatWindow.append("\n something messed up sending message!");
        }
    }
    
    //update chatWindow
    private void showMessage(final String m){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        chatWindow.append(m); 
                    }
                }
        );
    }
    
    //gives user permission to type crap into the text box
    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
             new Runnable(){
                 public void run(){
                     userText.setEditable(tof);
                 }                 
             }
        );
    }
    
    public String createXMLString(String inText){
        xmlString= "<message name="+ "\"" + clientName + "\"" + "><text color=" + "\""+ outhexColor + "\"" + ">"+inText+"</text></message>";
        System.out.println(xmlString);
        return xmlString; 
    }
    
    
   
}
