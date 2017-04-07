import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ServerReceive implements Runnable {

	Server s;
	Socket cs;
	
	public ServerReceive(Server s,Socket cs) {
	this.s=s;this.cs=cs;
	}
	
	@Override
	public void run() {


		try{

			Scanner sc = new Scanner(cs.getInputStream());
			while(true){

				
				
					String m=sc.nextLine();
				//	System.out.println("mahmoud");
				//	System.out.println("salem++"+m);
					
					s.processServers(m,this);
					
					}
		}	catch(IOException a)
		{
				}

	
	}
	
	
	
	
	
	
	
}
