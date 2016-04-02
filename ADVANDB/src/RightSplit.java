
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

public class RightSplit implements ActionListener {

	JPanel jPanel = new JPanel();

	private DBConnect db = new DBConnect();
	private MainGUI mainGUI;
	
	private JTabbedPane jtp = new JTabbedPane();
	private TransactionTab transactionTab;
	private JButton btnDelTab;
	private JTextField txtTabName;
	private JTable transactionsTable = new JTable();
	private JButton btnNewButton;
	private ArrayList<String> transactions = new ArrayList<>();

	public RightSplit(MainGUI mainGUI) {

		this.mainGUI = mainGUI;
		jPanel.setLayout(new MigLayout("", "[73px][10px][166px][400px][99px]", "[253px][23px][209px][23px]"));
		jPanel.add(jtp, "cell 0 0 5 1,grow");
		
		btnDelTab = new JButton("Del Tab");

		TransactionTab jp1 = new TransactionTab(mainGUI);
		TransactionTab jp2 = new TransactionTab(mainGUI);

		jtp.addTab("Tab1", jp1.getJPanel());
		jtp.addTab("Tab2", jp2);

		jPanel.setSize(800, 600);

		JButton btnAddTab = new JButton("Add Tab");
		
		DefaultTableModel model = new DefaultTableModel(new Object[] { "Transactions"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		transactionsTable.setModel(model);

		btnAddTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtp.addTab(txtTabName.getText(), new TransactionTab(mainGUI));
				transactions.add(txtTabName.getText());
			}
		});

		jPanel.add(btnAddTab, "cell 0 1,alignx left,aligny top");

	
		
		btnDelTab.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {

			        int index = jtp.indexOfTab("Glenn");
			        if (index >= 0) {
			            jtp.removeTabAt(index);
			        }

			    }
		});
		jPanel.add(btnDelTab, "cell 0 3 3 1,growx,aligny top");
		
		txtTabName = new JTextField();
		jPanel.add(txtTabName, "cell 2 1,grow");
		txtTabName.setColumns(10);
		
		jPanel.add(transactionsTable, "cell 0 2 3 1,grow");
		
		btnNewButton = new JButton("New button");
		jPanel.add(btnNewButton, "cell 4 1,alignx left,aligny top");

	}

	public JPanel getJPanel() {
		return jPanel;
	}

	private class ActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// if (e.getSource() == btnLogin) {
			//
			// }
			//
			// else {
			//
			// }
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}