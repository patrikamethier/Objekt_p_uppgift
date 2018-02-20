/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_uppgift.Objekt_p_uppgift;
import javax.swing.JFrame;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author patrikamethier
 */
public class ClientFrame extends JFrame{
    private JTextField portText;
    private JTextField networkText;
    private JTextField nameField;
    private JTextField colorField;
    public String color; 
    public String name;
    public int portnr; 
    public String ipnr; 
    
    
    public ClientFrame(){
        super("ClientFrame");
        FlowLayout myLayout = new FlowLayout();
        this.setLayout(myLayout);
        this.setPreferredSize(new Dimension(900, 100));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorField = new JTextField();
        colorField.setEditable(true);
        colorField.setColumns(10);
        colorField.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent event3){
                        color = event3.getActionCommand();
                    }
                }
        );
        nameField = new JTextField();
        nameField.setEditable(true);
        nameField.setColumns(10);
        nameField.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent event0){
                        name = event0.getActionCommand();
                    }
                }
        );
        portText = new JTextField();
        portText.setEditable(true);
        portText.setColumns(10);
        portText.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent event1){
                        String p = event1.getActionCommand();
                        portnr = Integer.valueOf(p);
                    }
                }
        
        
        );
        networkText = new JTextField();
        networkText.setEditable(true);
        networkText.setColumns(10);
        networkText.addActionListener(
                new ActionListener(){
                   public void actionPerformed(ActionEvent event2){
                       Thread clientThread = new Thread() {
                    public void run() {
                        ipnr = event2.getActionCommand();
                        CreateClient(portnr, ipnr, name, color);
                                     }
                   
                     };
                    clientThread.start();
                       //sendMessage(event.getActionCommand());
                       //userText.setText("");
                   }
                }
        );
        add(new Label("Color"));
        add(colorField); 
        add(new Label("Name"));
        add(nameField);
        add(new Label("Port"));
        add(portText);
        add(new Label("Network"));
        add(networkText);
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        
        
    }
     
        //Client requires both a IP adress and a port 
        //own IP is "127.0.0.1"
        //port is usually 6789
        //don't forget to hit enter in both the text fields
      public static void CreateClient(int port, String ip, String inname, String incolor){
      Client charlie = new Client(port, ip);
      charlie.clientName = inname;
      charlie.outhexColor = incolor; 
      //charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      charlie.startRunning(port);
    }
}
