/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author frede
 */
public class AlertWaiting extends JFrame implements KeyListener{
    Client client;
    String title;
    int id;
    public AlertWaiting(Client client, int id, String title){
        this.client = client;
        this.id = id;
        this.title = title;
        
        this.setAlwaysOnTop(true);
        
        
        this.addKeyListener(this);
        
        this.setLocationRelativeTo(null);
        this.requestFocus();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont (titleLabel.getFont ().deriveFont (32.0f));
        this.add(titleLabel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyChar());
        switch(ke.getKeyChar()){
            case 'a':
                client.sendRaw("post;;"+id);
                this.dispose();
                break;
            case 's':
                client.sendRaw("spoil;;"+id);
                this.dispose();
                break;
            case 'r':
                client.sendRaw("remove;;"+id);
                this.dispose();
                break;
                
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

}
