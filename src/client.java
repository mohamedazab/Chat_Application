import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client {
	String host;
	int port;
	Socket so;
	
	public client(String host,int port) throws UnknownHostException, IOException {
		this.host=host;this.port=port;
		
			
			 so = new Socket(host, port);
			

		
}
	
	void clientSend(String message){
		try {
			Scanner sc=new Scanner(System.in);
			PrintStream p = new PrintStream(so.getOutputStream());
			
		//	System.out.println(message);
				
			//	String s=sc.nextLine();
				p.println(message);
				
				
				if(message.toLowerCase().equals("bye")||message.toLowerCase().equals("quit")){
					
					
					so.close();
					
				}
				
			
				
			
			
			
		} catch (IOException e) {
			System.out.println("Connection lost with the server");
		}
		
	}
	
	public static void main(String[] args)
	{
		
		
	
		
		
		
		
		
		
		
	}
	
}