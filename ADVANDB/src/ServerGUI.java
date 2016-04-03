import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

public class ServerGUI implements ActionListener {

	private JPanel jPanel;
	private UDPServer udpServer;
	private MainGUI mainGUI;

	public ServerGUI() throws Exception {

		udpServer = new UDPServer();


		jPanel.setLayout(null);
		jPanel.setSize(1100, 600);

	}

	public JPanel getJPanel() {
		return jPanel;
	}

	private class ActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		ServerGUI serverGUI = new ServerGUI();
	}
}