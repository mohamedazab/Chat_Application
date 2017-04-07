

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class serverGUI extends JFrame  {
	
	private JTextArea  event;
	private JTextField tPortNumber;
  

    
    serverGUI(int port) throws Exception {
    	
    	        super("Server");
    	        
    	getContentPane().setBackground(Color.GREEN);
    	
    	        
    	
    	
    	        JPanel north = new JPanel();
    	        north.setBackground(Color.RED);
    	
    	        JLabel lblPortNumber = new JLabel("Port Number: ");
    	        lblPortNumber.setFont(new Font("Vijaya", Font.BOLD, 21));
    	        north.add(lblPortNumber);
    	
    	        tPortNumber = new JTextField("  " + port);
    	
    	        north.add(tPortNumber);
    	
    	        
    	
    	      
    	        getContentPane().add(north, BorderLayout.NORTH);
    	
    	
    	        JPanel center = new JPanel(new GridLayout(2,1));
    	
    	
    	
    	        event = new JTextArea(80,80);
    	        event.setBackground(new Color(238, 130, 238));
    	
    	        event.setEditable(false);
    
    	
    	        center.add(new JScrollPane(event));
    	
    	        getContentPane().add(center);
    
    	
    	
    	        setSize(400, 600);
    	
    	        setVisible(true);
    	        
    			this.repaint();
    			this.validate();
    		      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	
    	    }     
    
    
	public static void main(String[] args) throws Exception {
		new serverGUI(6000);
	}


	
}
