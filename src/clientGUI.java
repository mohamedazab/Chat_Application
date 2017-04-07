
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class clientGUI extends JFrame implements ActionListener {
   JTextField toenter , server , portnumber ;
   JButton login , logout , view_online,view_online_Local,send;
   JTextArea chatting_place ;
   boolean connected ;
   client c ;
   String host ;
   int port ;
   ClientRecieve Recieve ;
   Thread recieve ;
   
   
   clientGUI(String host , int port ){
		super("Client");
		this.port = port;
		this.host = host;
		JPanel northPanel = new JPanel(new GridLayout(3, 1));
		northPanel.setBackground(Color.RED);
		JPanel serverAndPort = new JPanel(new GridLayout(1, 5, 1, 3));
		serverAndPort.setBackground(Color.BLUE);
		server = new JTextField(host);
		portnumber = new JTextField("" + port);
		portnumber.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel label = new JLabel("Server Address:  ");
		label.setFont(new Font("Caladea", Font.BOLD, 15));
		serverAndPort.add(label);
		serverAndPort.add(server);
		JLabel label_1 = new JLabel("Port Number:  ");
		label_1.setFont(new Font("Caladea", Font.BOLD, 15));
		serverAndPort.add(label_1);
		serverAndPort.add(portnumber);
		serverAndPort.add(new JLabel(""));
		northPanel.add(serverAndPort);
	//	toenter = new JTextField("Your username goes here");
	//	toenter.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
    //    toenter.setBackground(Color.YELLOW);
		
	//	northPanel.add(toenter);

	//	getContentPane().add(northPanel, BorderLayout.NORTH);

		chatting_place= new JTextArea("Entering the chat ", 80, 80);
		chatting_place.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		chatting_place.setBackground(Color.CYAN);

		JPanel centerPanel = new JPanel(new GridLayout(1, 1));

		centerPanel.add(new JScrollPane(chatting_place));

		chatting_place.setEditable(false);
		

		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		login = new JButton("Login");
		login.setFont(new Font("MV Boli", Font.BOLD, 15));
		login.setBackground(Color.GREEN);

		login.addActionListener(this);

		logout = new JButton("Logout");
		logout.setFont(new Font("MV Boli", Font.BOLD, 15));
		logout.setBackground(Color.RED);
		

		logout.addActionListener(this);

		logout.setEnabled(false);
		view_online = new JButton("View Online");
		view_online.setFont(new Font("MV Boli", Font.BOLD, 15));
        view_online.setBackground(Color.MAGENTA);
        
		view_online.addActionListener(this);

		view_online.setEnabled(false);
		view_online_Local = new JButton("View Online_local");
		view_online_Local.setFont(new Font("MV Boli", Font.BOLD, 15));
		view_online_Local.setBackground(Color.MAGENTA);
        
		view_online_Local.addActionListener(this);

		view_online_Local.setEnabled(false);
		send = new JButton("Send");
		send.setFont(new Font("MV Boli", Font.BOLD, 15));
		send.setBackground(Color.MAGENTA);
        
		send.addActionListener(this);

		send.setEnabled(false);
		JPanel southPanel = new JPanel();

		southPanel.add(login);
		southPanel.add(send);
		southPanel.add(view_online_Local);

		southPanel.add(logout);

		southPanel.add(view_online);

		getContentPane().add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(600, 600);

		setVisible(true);

	//	toenter.requestFocus();
   
		this.repaint();
		this.validate();
	      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }
   
   
   
   void append(String str) {
	 
		chatting_place.append(str);
		chatting_place.setCaretPosition(chatting_place.getText().length() - 1);
	       }
   void connectionFailed() {
	   
	           login.setEnabled(true);
	   
	           logout.setEnabled(false);
	   
	           view_online.setEnabled(false);	   
	           toenter.setText("Anonymous");
	      //     toenter.setText("" + port);
	           server.setText(host);

	           server.setEditable(false);
	  
	           portnumber.setEditable(false);
	   
	           
	   
	     //      toenter.removeActionListener(this);
	   
	           connected = false;
	   
	       }

   
   
   
   public void showError(String error){
	   final JPanel panel = new JPanel();
	   JOptionPane.showMessageDialog(panel, "Could not open file", error, JOptionPane.ERROR_MESSAGE);
	   
   }
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JButton){
			JButton bt=(JButton)e.getSource();
			if(bt.getText().equals("Login")){
				if(!connected){
				String r=JOptionPane.showInputDialog(this,"Enter Your Name");
				if(r!=null&&r!="")
				{
					try{
				//		System.out.println(r);
					 c=new client(host,port);
				//	System.out.println(c.so);
					if(c.so != null){
						
					Recieve = new ClientRecieve(c.so,this);
					recieve=new Thread(Recieve);
					recieve.start();
				//	Thread send = new Thread(new clientSend(c.so));
				//	send.start();
					c.clientSend(r);
				//	System.out.println(r);
				//	System.out.println(recieve);
				//	System.out.println();
					}
					else{
						JOptionPane.showMessageDialog(this,"the server is not available try again later");	

					}
					}
					catch(Exception er){
						JOptionPane.showMessageDialog(this,"the server is not available try again later");	

					}
				}
				}
				
			}
			if(bt.getText().equals("Logout"))
				if(connected){c.clientSend("bye");
			chatting_place.setText("");
			login.setEnabled(true);
			chatting_place.setEditable(false);
			logout.setEnabled(false);
			view_online.setEnabled(false);
			view_online_Local.setEnabled(false);
			send.setEnabled(false);
			connected=false;
			
				}
			if(bt.getText().equals("View Online"))
				if(connected){c.clientSend("getMemberlist()");
				///all  member list  //// 
				}
			if(bt.getText().equals("View Online_local"))
				if(connected){c.clientSend("getmembers()");
				/// my member list only //// 
				}
			if(bt.getText().equals("Send"))
				if(connected){
				 
					String [] s = chatting_place.getText().split("\n") ;
					c.clientSend(s[s.length-1]);
				}
				else
					JOptionPane.showInputDialog(this,"you are not online");
		}

		
	}
	
	



	public void input(String s) {
	//	System.out.println("salem");
		//System.out.println(s);
		chatting_place.setText(chatting_place.getText()+"\n"+s);
		if(s.startsWith("you have succesfully registered")){
			login.setEnabled(false);
			chatting_place.setEditable(true);
			logout.setEnabled(true);
			view_online.setEnabled(true);
			view_online_Local.setEnabled(true);
			send.setEnabled(true);
			connected=true;
		}
		
	}
	
	
	public static void main(String[] args) {
	new clientGUI("192.168.1.101", 6000);
	}
}
