import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClientThread implements Runnable {

	Server s;
	Socket cs;
	String name;
	
	public ClientThread(Server s,Socket cs,String name) {
	this.s=s;this.cs=cs;this.name=name;
	}
	
	@Override
	public void run() {


		try{

			Scanner sc = new Scanner(cs.getInputStream());
			while(sc.hasNextLine()){

				

					String m=sc.nextLine();
					
					
			//		System.out.println(name+" send "+m);
					
					
					s.process(m, name,this);
					}
		}	catch(IOException a)
		{
				}

	
	}
	
	
	
	
	
	
	
}
