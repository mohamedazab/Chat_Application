

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class clientSend implements Runnable {

	
	
	Socket client;//server
	
	public clientSend(Socket client) {
		
		this.client=client;
	
	}
	
	
	
	@Override
	public void run() {
	
		
		
		try {
			Scanner sc=new Scanner(System.in);
			PrintStream p = new PrintStream(client.getOutputStream());
			
			while(true){
				
				String s=sc.nextLine();
				System.out.println(s);
				p.println(s);
				
				
				if(s.toLowerCase().equals("bye")||s.toLowerCase().equals("quit")){
					
					
					client.close();
					
					break;
				}
				
			
				
			}
			
			
		} catch (IOException e) {
			System.out.println("Connection lost with the server");
		}
		
	}

}
