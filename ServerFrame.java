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
public class ServerFrame extends JFrame{
    private JTextField portText;
    private JTextField nameField;
    private JTextField colorField;
    public String color;
    public String name; 
    
    
    //public static void main(String[] args){
        //new ServerFrame();
    //}
    
    
    public ServerFrame(){
        super("ServerFrame");
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
                   public void actionPerformed(ActionEvent event){
                       Thread clientThread = new Thread() {
                    public void run() {
                        String s = event.getActionCommand();
                        int i = Integer.valueOf(s);
                         CreateServer(i, name, color);
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
        pack();
        // SsetSize(300, 150);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
    }
   
    
    //server requires a port 6789
    public static void CreateServer(int port, String inname, String incolor){
        Server sally = new Server();
        sally.serverName = inname; 
        sally.outhexColor = incolor; 
        //sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sally.startRunning(port);
    }
    
}
    
