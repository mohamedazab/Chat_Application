import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame implements Runnable {
	ServerSocket me ;
	static TreeMap<String,ClientThread> clients ;//clients
	static ArrayList<Socket> ServerSockets ;//sockets to send from
	static TreeMap<String,Thread> clientThreads ;//clients
	ServerSocket connectServers ;
	ArrayList<ServerReceive>servers;
	ArrayList<String> Allmembers;
	private JTextArea  event;
	private JTextField tPortNumber;

	public Server(ServerSocket cs, ServerSocket connectServers)
	{super("Server");
		this.me= cs;
		clients=new TreeMap<String,ClientThread>();
		ServerSockets=new ArrayList<>();
		clientThreads=new TreeMap<>();
		this.connectServers=connectServers;
		servers=new ArrayList<>();
		Allmembers=new ArrayList<>();
		///check validity of the name       
	    	getContentPane().setBackground(Color.GREEN);
	    	
	    	        
	    	
	    	
	    	        JPanel north = new JPanel();
	    	        north.setBackground(Color.RED);
	    	
	    	        JLabel lblPortNumber = new JLabel("Port Number: ");
	    	        lblPortNumber.setFont(new Font("Vijaya", Font.BOLD, 21));
	    	        north.add(lblPortNumber);
	    	
	    	        tPortNumber = new JTextField("  " + cs.getLocalPort());
	    	
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
	    	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	

	}

	private void connectServers() {

		////	change to 3
		for (int  i = 0; i <3; i++) 

		{

			Socket cs=null;

			try{
				cs=connectServers.accept();

				///////////

				//	System.out.println("new server online "+i);
				//	p.println("done");
				//System.out.println(cs+" salem "+this);
				//System.out.println("connected with server");
				ServerReceive sr=new ServerReceive(this, cs);
				servers.add(i, sr);
				Thread r=new Thread(sr);
				r.start();





			}catch(Exception e){

				//System.out.println("3azab");

			}





		}
	/*	for (int i = 0; i < servers.size(); i++) {
			for (int j = i+1; j < servers.size(); j++) {
				if(servers.get(j).cs.getLocalPort() <servers.get(i).cs.getLocalPort())
				{
				ServerReceive	temp = servers.get(j);
					servers.set(j, servers.get(i)) ;
					servers.set(i,temp);
				}
			}
			
		}*/
	//	System.out.println(servers.toString());
		



	}
	@Override
	public void run() {
		
	
	
		connectServers();

		while(true){

			Socket cs=null;
			try{
				cs=me.accept();

				///////////
				Scanner sc = new Scanner(cs.getInputStream());
				PrintStream p = new PrintStream(cs.getOutputStream());
				String ff = sc.nextLine();
				//System.out.println("idid");
				//System.out.println(ff);
				if(!valid(ff))
				{

					p.println("Incorrect Command :you must choose a unique name ");

				}
				else{
					//System.out.println("valid");
					p.println("you have succesfully registered as "+ff);
					ClientThread sr=new ClientThread(this, cs, ff);
					Thread r=new Thread(sr);
					clientThreads.put(ff, r);
					r.start();
					Allmembers.add(ff);
					clients.put(ff,sr);
					event.setText(event.getText()+"\n"+ff+" is connected to the server");
					
					for (int j = 0; j <ServerSockets.size(); j++) {
						//	System.out.println(servers.get(j).cs);
							if(ServerSockets.get(j)!=null){
								try {
									PrintStream s = new PrintStream(ServerSockets.get(j).getOutputStream());
									s.println("#"+ff);			
								}
								catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}

				}


			}catch(Exception e){

				e.printStackTrace();
				if(cs!=null)
					try {
						cs.close();
					System.out.println("client socket exception");
					} catch (IOException e1) {
						e1.printStackTrace();
					}

			}





		}






	}

	public boolean valid(String name){

		return !Allmembers.contains(name);
	}




	public void processServers(String message ,ServerReceive s) throws IOException{

		///wrong 
		int idx=servers.indexOf(s);
		//System.out.println(s);
		PrintStream ps = new PrintStream(ServerSockets.get(idx).getOutputStream());
		if(message.charAt(0)=='#'){
			//	System.out.println("servers said valid");
			Allmembers.add(message.substring(1));

		}
		else if(message.charAt(0)=='*')
			Allmembers.remove(message.substring(1));
		
		else{
								String sp[]=message.split("--");
								String Sender=sp[0];
								String receiver=sp[1];
								String Cmess=sp[2];
								int TTL=sp[3].charAt(0);
									ClientThread r=clients.get(receiver);
									if(r!=null){
									System.out.println(Arrays.toString(sp));
									this.chat("@"+receiver+">>"+Cmess, Sender,TTL, r);
									}

							}



	}

	
	public void removeClient(String name){
		Allmembers.remove(name);
		for (int j = 0; j <ServerSockets.size(); j++) {
			//	System.out.println(servers.get(j).cs);
				if(ServerSockets.get(j)!=null){
					try {
						PrintStream sp = new PrintStream(ServerSockets.get(j).getOutputStream());
						sp.println("*"+name);			
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
	}
	public String myclients(){
		String mymem="members:";
		for (Map.Entry<String,ClientThread>entr:clients.entrySet()) {
			
			mymem+=" "+entr.getKey();
		}
		return mymem;
		
	}
	public void process(String s,String name,ClientThread m){


		try {
			PrintStream p=new PrintStream(m.cs.getOutputStream());

			if(s.toLowerCase().equals("bye")||s.toLowerCase().equals("quit"))
			{
				try {
					m.cs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				clients.remove(name);
				clientThreads.remove(name);
				event.setText(event.getText()+"\n"+name+" is disconnected from the server");
				this.removeClient(name);


			}else
				if(s.toLowerCase().equals("getmemberlist()"))
				{
					//System.out.println("members");
					try {
						p.println(memberList(m));
					} catch (IOException e) {
						e.printStackTrace();
					}//////////////add member list of the other server

				}
				else if (s.toLowerCase().equals("getmembers()"))
				{
				
					p.println(myclients());
				}
				else 
					this.chat(s,name,2,m);




		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




	}



	public String memberList(ClientThread m) throws IOException{
		String members  = "mem : " ;
		members +=this.Allmembers.toString();

		return members;



	}
	



	protected  void chat(String s, String sender,int TTL,ClientThread m){
		//////////////TTL 
		if(TTL<1)return;
		PrintStream p;
		try {
			Socket cs=m.cs;

			p = new PrintStream(cs.getOutputStream());
			if(s.length()>0&&s.charAt(0) != '@')
			{
				p.println("Invalid command");
				return;
			}
			if(!s.contains(">>"))
			{	p.println("Invalid command");return;}
			String pr[]=s.split(">>");
			//System.out.println(Arrays.toString(pr));
			String message =pr[1];
			String reciever =pr[0].substring(1) ;






			if(clients.containsKey(reciever)){


				PrintStream f = new PrintStream(clients.get(reciever).cs.getOutputStream());

				f.println(sender+">> : "+message);


			}
			else{

				//TTL


				for (int i = 0; i < 3; i++) {

					//System.out.println(servers.toString());
					Socket sr=ServerSockets.get(i);
					if(sr!=null){
 						PrintStream ps= new PrintStream(ServerSockets.get(i).getOutputStream());
						ps.println(sender+"--"+reciever+"--"+message+"--"+(TTL-1));

					}

				}
			}






		} catch (IOException e) {
			e.printStackTrace();
		}


	}










}