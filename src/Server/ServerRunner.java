package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerRunner {
	
	private static DatagramSocket socket;
	private static boolean running;
	private static int idClient;
	
	public static ArrayList<InformationClient> listClient = new ArrayList<InformationClient>();

	public static void start(int port) {
		
		try {
			socket = new DatagramSocket(port);
			running =true;
			listen();
			
			System.out.println("Server connected on port "+port); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Send a message to everyone
	public static void broadcast(String m) {
		
		for(InformationClient c : listClient) {
			send(m,c.getAddress(),c.getPort());
			
		}
		
	}
	
	//Sending message for a person
	public static void send(String m,InetAddress address,int port) {
		try {
			
			m += "\\e";
			byte[] buffer = m.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address,port);
			socket.send(packet);
			System.out.println("TEST "+address.getHostAddress());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	//Thread
	public static void listen() {
		
		Thread listenThread = new Thread("Listener") {
			public void run() {
				
				try {
					while(running) {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);
						
						String m = new String(buffer);
						m = m.substring(0,m.indexOf("\\e"));
						
						
						
						if(!isServerCommand(m,packet)) {
							broadcast(m);
						}
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				
			}
		};
		listenThread.start();
			
		
		
		
	}
	
	//CON is used to connect and ":" it's a delimiter
	
	private static boolean isServerCommand(String m, DatagramPacket packet) {
		
		if (m.startsWith("\\con:")) {
			
			String name = m.substring(m.indexOf(":")+1);
			listClient.add(new InformationClient(packet.getAddress(), name,idClient++, packet.getPort()));
			broadcast("User "+name+ " connected");
			
			return true;
		}
		
		return false;
	}
	
	
	//Stop method
	public static void stop() {
		running = false;
		
	}
	
}
