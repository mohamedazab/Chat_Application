/*
import java.io.IOException;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ServerTMP implements Runnable {
	 ServerSocket me ;
	 static TreeMap<String,ClientThread> sockets ;//clients
	 ServerSocket connectServers ;
	
	ServerReceive[] servers;
	public ServerTMP()
	{
		//this.me= cs;
		this.connectServers=connectServers;
		sockets=new TreeMap<String,ClientThread>();

		
		ServerSocket servSock;
		ServerSocket serversConnect;
		try {
			servSock = new ServerSocket(600);
			serversConnect = new ServerSocket(500);
			this.connectServers=serversConnect;
			this.me=servSock;
			
			
			/// run other servers 
			
						this.servers=new ServerReceive[1];

			/// connect to the other servers
			while(true){
				try{
			Socket tr1=new Socket("192.168.1.101",500);
			this.servers[0]=new ServerReceive(this, tr1);
			System.out.println(12);
			break;
				}
				 catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			}
			
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
		

	}

	@Override
	public void run() {


		

			

			while(true){
				
				Socket cs=null;
				try{
				 cs=me.accept();
			
			Scanner sc = new Scanner(cs.getInputStream());
			PrintStream p = new PrintStream(cs.getOutputStream());
				String ff = sc.nextLine();
				if(!validMember(ff))
				{
					p.println("Incorrect Command :you must choose a unique name ");

				}
				else{
					ClientThread sr=new ClientThread(this, cs, ff);
				sockets.put(ff,sr);
					p.println("you have succesfully registered as "+ff);
					break;
				}

				
				}catch(Exception e){
					
					if(cs!=null)
						try {
							cs.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					
				}
				
				
				
				
				
			}

			

		


	}

	public boolean valid(String name){
		return sockets.containsKey(name);		
	}
	public boolean validMember(String name) throws IOException{
	
				
		boolean f=valid(name);		
		for (int j = 0; j < servers.length; j++) {
			//send 
			if(servers[j]!=null){
			Scanner sc = new Scanner(servers[j].cs.getInputStream());
			PrintStream s = new PrintStream(servers[j].cs.getOutputStream());
			s.println("#"+name);
			String in =sc.nextLine();
			f&=in.equals("1");
			if(f==false){
				sc.close();
				return false;
			}
			}
			
		}
		return f;
		
	}
	
	
	public void processServers(String message ,ServerReceive s) throws IOException{
		
		
		PrintStream ps = new PrintStream(s.cs.getOutputStream());
		
		if(message.charAt(0)=='#'){
			String send=valid(message.substring(1, message.length()))?"1":"0";
			ps.println(send);
			
			
		}
		if(message.equals("members"))
			ps.println(this.memberHelp());
		
		
		String sp[]=message.split("$");
		if(this.sockets.containsKey(sp[1])){
			ClientThread r=sockets.get(sp[1]);
			// sender$reciever$message
			//@reciever sender message
			this.process("@"+sp[1]+" "+sp[2], sp[0], r);
			
			
		}
		
		
		
	}
	
	public void process(String s,String name,ClientThread m){
		
		
		
			if(s.toLowerCase().equals("bye")||s.toLowerCase().equals("quit"))
			{
				try {
					m.cs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sockets.remove(name);
				System.out.println("client disconnected "+name);


			}else
			if(s.toLowerCase().equals("getmemberlist()"))
			{
				try {
					memberList(m);
				} catch (IOException e) {
					e.printStackTrace();
				}//////////////add member list of the other server

			}
			else 
				this.chat(s,name,m);

			
		
		
		
	}
	
	

public String memberList(ClientThread m) throws IOException{
	String members  = "members+\n" ;
	members +=this.memberHelp();
	
	for (int i = 0; i < servers.length; i++) {
		
		Scanner sc = new Scanner(servers[i].cs.getInputStream());
		PrintStream s = new PrintStream(servers[i].cs.getOutputStream());
		s.println("members");
		String in =sc.nextLine();
		members+="\n"+in;
		
	}
	return members;
	
	
	
}
public String memberHelp(){
	
	String members  = "" ;
	for(Map.Entry<String, ClientThread> entry : sockets.entrySet()) {
		String key = entry.getKey();
		members = members+" \n"+key ;
	}
	return members;
	
	
}



protected  void chat(String s, String sender,ClientThread m){
	//////////////TTL 
PrintStream p;
try {
	Socket cs=m.cs;
	
	p = new PrintStream(cs.getOutputStream());
	if(s.charAt(0) != '@')
	{
		p.println("Invalid command");
		return;
	}
	String pr[]=s.split(" ");
	String message =pr[1];
	String reciever =pr[0].substring(1) ;
			
			
		
	

	
	if(sockets.containsKey(reciever)){
		
		
		PrintStream f = new PrintStream(sockets.get(reciever).cs.getOutputStream());

		f.println(sender+">> : "+message);
		

	}
	else{
		
		//TTL
		
		
		for (int i = 0; i < servers.length; i++) {
			
			Scanner sc = new Scanner(servers[i].cs.getInputStream());
			PrintStream ps= new PrintStream(servers[i].cs.getOutputStream());
			ps.println(sender+"$"+reciever+"$"+message);
			
			
		}
	}




	
	
} catch (IOException e) {
	e.printStackTrace();
}

	
}

}
*/