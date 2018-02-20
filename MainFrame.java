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
public class MainFrame extends JFrame {
    
   
    public JButton clientButt;
    public JButton serverButt;
    //public Client charlie;
    //public Server sally;
    
    public static void main(String[] args){
        new MainFrame();
    }
   
    public MainFrame(){
        super("Main Frame Message");
        
        FlowLayout myLayout = new FlowLayout();
        this.setLayout(myLayout);
        this.setPreferredSize(new Dimension(400, 150));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        clientButt= new JButton("Client");
        clientButt.setPreferredSize(new Dimension(100, 40));
        clientButt.addActionListener(
           new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    new ClientFrame();
               }
            }
        );
        serverButt= new JButton("Server");
        serverButt.setPreferredSize(new Dimension(100, 40));
        serverButt.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    new ServerFrame();
                }
           }
        );
    
        
        this.add(new Label("Connect as Client or Server"));
        this.add(clientButt);
        this.add(serverButt);
        this.pack();
        this.setVisible(true);
        serverButt.setOpaque(true);
        clientButt.setOpaque(true);
        
        
    }
    
   
  
}