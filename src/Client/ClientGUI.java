package Client;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;


public class ClientGUI {
	
	private static BufferedReader buff = null;

	private JFrame frmChatRoom;
	private JTextField message;
	private static JTextArea showMessage;
	private static String name;
	private Client client;
	private JPanel panel1;
	private static JTextArea showOldMessage;
	private JButton oldMessage;

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
		showOldMessage = new JTextArea();
		showOldMessage.setColumns(37);
		
		
		
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
		
		
		
		
		
		panel1 = new JPanel();
		FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
		fl_panel1.setAlignOnBaseline(true);
		frmChatRoom.getContentPane().add(panel1, BorderLayout.EAST);
		
		
		showOldMessage.setRows(27);
		showOldMessage.setEditable(false);
		panel1.add(showOldMessage);
		
		oldMessage = new JButton("Old Message");
		oldMessage.addActionListener(e -> {
			
			showOldMessage.setText("");
			readAndShowOldMessages();
		});
		panel1.add(oldMessage);
		
		frmChatRoom.setLocationRelativeTo(null);
		
		
	}
	
	/**
	 * Prints the text.
	 *
	 * @param m the m
	 */
	public static void printText(String m) {
		
		showMessage.setText(showMessage.getText()+m+"\n");
	}
	
	public static void printTextOld(String m) {
		
		showOldMessage.setText(showOldMessage.getText()+m+"\n");
	}
	
	
	
	
	public static void readAndShowOldMessages() {
		
		File f = new File("files/message/message.txt");
		
		try {
			FileReader fileReader = new FileReader(f);
			buff = new BufferedReader(fileReader);
			
			
			String line;
			
			while((line = buff.readLine()) != null) {
				printTextOld(line);
				
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			buff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
