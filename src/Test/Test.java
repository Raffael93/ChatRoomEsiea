package Test;

import static org.junit.Assert.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import org.junit.Before;

import Client.Client;
import Server.InformationClient;
import Server.ServerRunner;

public class Test {

	
	private Client client;
	private ArrayList<InformationClient> listClientTest;
	
	private String message;
	private boolean isRunning;
	private static DatagramSocket socket;
	
	@Before
	public void setUp() {
		client = new Client("Raffael","127.0.0.1",4242);
		
		
		
		message = "Bonjour";
		isRunning = true;
		
		listClientTest = new ArrayList<>();
		
		try {
			socket = new DatagramSocket(4242);
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	public void testSend() {
		
		
		assertEquals(message,"Bonjour","Bonjour");
	}
	
	public void testListen() {
		
		Thread listenThread = new Thread("Listener") {
			public void run() {
				
				try {
					while(isRunning) {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);
						
						String m = new String(buffer);
						m = m.substring(0,m.indexOf("\\e"));
						
						
						
						if(!ServerRunner.isServerCommand(m,packet)) {
							ServerRunner.broadcast(m);
						}
						

					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				
			}
		};
		listenThread.start();
			
		
		
		
		assertEquals(message,"Bonjour","Bonjour");
	}
	
	public void testBroadcast() {
		
		for(InformationClient cl : listClientTest) {
			ServerRunner.send(message,cl.getAddress(),cl.getPort());
			
		}
		assertEquals(message,"Bonjour","Bonjour");
	}
	
	
	
}
