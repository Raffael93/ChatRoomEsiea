package Server;

import java.net.InetAddress;

public class InformationClient {

	private InetAddress address;
	
	private String name;
	private int id;
	private int port;
	
	/**
	 * @param address
	 * @param name
	 * @param id
	 * @param port
	 */
	public InformationClient(InetAddress address, String name, int id, int port) {
		this.address=address;
		this.name = name;
		this.id = id;
		this.port = port;
		
	}

	public InetAddress getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getPort() {
		return port;
	}
	
	
	
	
	
	
	
	
	
	
}
