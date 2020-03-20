package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientGUI {

	private JFrame frmChatRoom;
	private JTextField message;
	private static JTextArea showMessage;
	private static String name;
	private Client client;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ClientGUI window = new ClientGUI();
					window.frmChatRoom.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ClientGUI() {
		
		initialize();
		name = JOptionPane.showInputDialog("Enter a name");
		client = new Client(name, "localhost", 4242);
	}


	private void initialize() {
		frmChatRoom = new JFrame();
		frmChatRoom.setTitle("Chat room ");
		frmChatRoom.setBounds(100, 100, 836, 573);
		frmChatRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showMessage = new JTextArea();
		
		
		
		JScrollPane scrollPane = new JScrollPane(showMessage);
		frmChatRoom.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frmChatRoom.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener( e -> {
			if(!message.getText().equals("")) {
				client.send(message.getText());
				message.setText("");
			}
	
				
		});
		
		message = new JTextField();
		panel.add(message);
		message.setColumns(90);
		panel.add(btnSend);
		
		frmChatRoom.setLocationRelativeTo(null);
		
	}
	
	public static void printText(String m) {
		
		showMessage.setText(showMessage.getText()+m+"\n");
	}

}
