package Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {
	
	
	/** The socket. */
	private DatagramSocket socket;
	
	/** The address. */
	private InetAddress address;
	
	/** The port. */
	private int port;
	
	/** The namee. */
	private String namee;
	
	/** The running. */
	private boolean running;
	
	/**
	 * Instantiates a new client.
	 *
	 * @param namee the namee
	 * @param address the address
	 * @param port the port
	 */
	public Client(String namee,String address, int port) {
		this.namee = namee;
		try {
			
			this.address=InetAddress.getByName(address);
			this.port = port;
			
			socket = new DatagramSocket();
			
			
			running = true;
			listen();
			send("--con:"+namee);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	/**
	 * Send.
	 *
	 * @param m the m
	 */
	public void send(String m) {
		
		try {
			if(!m.startsWith("--")) {
				m = namee +" : " + m;
			}
			
			
			m += "\\e";
			byte[] buffer = m.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address,port);
			socket.send(packet); 
			System.out.println("Sent a message to "+address.getHostAddress()+":"+port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Listen.
	 */
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
	
	

	/**
	 * Checks if is server command.
	 *
	 * @param m the m
	 * @param packet the packet
	 * @return true, if is server command
	 */
	private static boolean isServerCommand(String m, DatagramPacket packet) {
		
		if (m.startsWith("--con:")) {
			
			return true;
		}
		
		return false;
	}
	
	
	
}
