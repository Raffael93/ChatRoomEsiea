package Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {
	
	
	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private String name;
	private boolean running;
	
	public Client(String name,String address, int port) {
		try {
			
			this.address=InetAddress.getByName(address);
			this.port = port;
			
			socket = new DatagramSocket();
			
			
			running = true;
			listen();
			send("\\con:"+name);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void send(String m) {
		
		try {
			m += "\\e";
			byte[] buffer = m.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address,port);
			socket.send(packet); 
			System.out.println("Sent a message to "+address.getHostAddress()+":"+port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void listen() {
		
		Thread listenThread = new Thread("Listener") {
			public void run() {
				
				try {
					while(running) {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);
						
						String m = new String(buffer);
						m = m.substring(0,m.indexOf("\\e"));	
						
						if(!isServerCommand(m, packet)) {
							ClientGUI.printText(m);
						}
						
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				
			}
		};
		listenThread.start();
			
		
		
		
	}
	

	private static boolean isServerCommand(String m, DatagramPacket packet) {
		
		if (m.startsWith("\\con:")) {
			
			return true;
		}
		
		return false;
	}
	
	
	
}
