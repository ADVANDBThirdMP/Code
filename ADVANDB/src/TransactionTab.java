
import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

public class TransactionTab extends JPanel implements ActionListener {

	
	
	
	JPanel jPanel = new JPanel();
	
	private DBConnect db = new DBConnect();
	private MainGUI mainGUI;
	private JButton btnRun;
	private UDPClient udpClient;
	public static JTextArea txtTransactionQuery;
	
	public TransactionTab(MainGUI mainGUI) throws Exception {

		this.mainGUI = mainGUI;
	
		udpClient = new UDPClient();
		jPanel.setLayout(null);
		
		txtTransactionQuery = new JTextArea();
		txtTransactionQuery.setBounds(30, 30, 535, 207);
		jPanel.add(txtTransactionQuery);
		
		btnRun = new JButton("Run");
		btnRun.setBounds(514, 242, 51, 23);
		jPanel.add(btnRun);
		
		jPanel.setSize(600, 300);


	}


	public JPanel getJPanel() {
		return jPanel;
	}

	private class ActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnRun) {
//				udpClient
//				JTabbedPane.
			}
		}

	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}