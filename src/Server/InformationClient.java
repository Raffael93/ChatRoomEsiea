package Server;

import java.net.InetAddress;

// TODO: Auto-generated Javadoc
/**
 * The Class InformationClient.
 */
public class InformationClient {

	/** The address. */
	private InetAddress address;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private int id;
	
	/** The port. */
	private int port;
	

	/**
	 * Instantiates a new information client.
	 *
	 * @param address the address
	 * @param name the name
	 * @param id the id
	 * @param port the port
	 */
	public InformationClient(InetAddress address, String name, int id, int port) {
		this.address=address;
		this.name = name;
		this.id = id;
		this.port = port;
		
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	
	
	
	
	
	
	
	
	
	
}
