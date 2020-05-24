package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import Client.Client;


// TODO: Auto-generated Javadoc
/**
 * The Class ServerRunner.
 */
public class ServerRunner {
	
	/** The socket. */
	private static DatagramSocket socket;
	
	/** The is running. */
	private static boolean isRunning;
	
	/** The id client. */
	private static int idClient;
	
	/** The client. */
	private Client client;
	
	/** The list client. */
	public static ArrayList<InformationClient> listClient = new ArrayList<InformationClient>();

	/**
	 * Start.
	 *
	 * @param port the port
	 */
	public static void start(int port) {
		
		try {
			socket = new DatagramSocket(port);
			isRunning =true;
			listen();
			
			System.out.println("Server connected on port "+port); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Broadcast.
	 *
	 * @param m the m
	 */
	//Send a message to everyone
	public static void broadcast(String m) {
		
		for(InformationClient c : listClient) {
			send(m,c.getAddress(),c.getPort());
			
		}
		
	}
	
	/**
	 * Send.
	 *
	 * @param m the m
	 * @param address the address
	 * @param port the port
	 */
	//Sending message for a person
	public static void send(String m,InetAddress address,int port) {
		try {
			
			m += "\\e";
			byte[] buffer = m.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address,port);
			socket.send(packet);
			//System.out.println("TEST "+address.getHostAddress());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	/**
	 * Listen.
	 */
	//Thread
	public static void listen() {
		
		Thread listenThread = new Thread("Listener") {
			public void run() {
				
				try {
					while(isRunning) {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);
						
						String m = new String(buffer);
						m = m.substring(0,m.indexOf("\\e"));
						
						
						
						if(!isServerCommand(m,packet)) {
							broadcast(m);
						}
						//File writer
						getMessageConstant(m);
						
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				
			}
		};
		listenThread.start();
			
		
		
		
	}
	
	// CON is used to connect and ":" it's a delimiter
	
	/**
	 * Checks if is server command.
	 *
	 * @param m the m
	 * @param packet the packet
	 * @return true, if is server command
	 */
	public static boolean isServerCommand(String m, DatagramPacket packet) {
		
		if (m.startsWith("--con:")) {
			
			String name = m.substring(m.indexOf(":")+1);
			listClient.add(new InformationClient(packet.getAddress(), name,idClient++, packet.getPort()));
			broadcast("User "+name+ " connected \nWelcome " + name +" to my soul society !\n");
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Stop.
	 */
	//Stop method
	public static void stop() {
		isRunning = false;
		
	}
	
	
	
	/**
	 * Gets the message constant.
	 *
	 * @param m the m
	 * 
	 */
	public static void getMessageConstant(String m) {

		Path path = Paths.get("files", "message","message.txt");
		
		String text = m + "\n";
		
		try {
			Files.write(path, text.getBytes(), 
					StandardOpenOption.CREATE,
					StandardOpenOption.WRITE,
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	

	
	
	
	
	
	
	
	
	
}
