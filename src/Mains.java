import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Mains implements Runnable {

	public static void main(String[] args) {

		ServerSocket servSock;
		ServerSocket serversConnect;
		try {
			servSock = new ServerSocket(6002);
			serversConnect = new ServerSocket(6003);
			Server s=new Server(servSock,serversConnect);
			Thread server=new Thread(s);
			server.start();
			/// run other servers 
			System.out.println("waiting");

			
			/// connect to the other servers
			while(true){
				try{
					Socket tr1=new Socket("localhost",6001);
					Socket tr2=new Socket("localhost",6005);
					Socket tr3=new Socket("localhost",6007);
					

					s.ServerSockets.add(0, tr1);
					s.ServerSockets.add(1, tr2);
					s.ServerSockets.add(2, tr3);
			//Thread servC=new Thread(s.servers.get(1));
			//servC.start();
		//	System.out.println(12);
			break;
				}
				 catch (IOException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					}
			
			}
			System.out.println("connected to all");

			
		//System.out.println(s.validMember("ahmed"));
			
			
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		
				
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
