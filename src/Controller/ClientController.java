package Controller;

import java.awt.EventQueue;

import javax.swing.UIManager;

import Client.ClientGUI;

public class ClientController {

	//Controller
	public static void controller() {
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
	
}
