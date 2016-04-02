
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ScrollPane;

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
	private JButton btnNewButton;
	private ArrayList<String> transactions = new ArrayList<String>();
	private JTable table = new JTable();
	private DefaultTableModel model;

	public RightSplit(MainGUI mainGUI) {

		this.mainGUI = mainGUI;
		jPanel.setLayout(new MigLayout("", "[73px,grow][10px][166px][400px][99px]", "[253px][23px][209px,grow][23px]"));
		jPanel.add(jtp, "cell 0 0 5 1,grow");
		
		btnDelTab = new JButton("Del Tab");

		TransactionTab jp1 = new TransactionTab(mainGUI);
		TransactionTab jp2 = new TransactionTab(mainGUI);

		jtp.addTab("Tab1", jp1.getJPanel());
		jtp.addTab("Tab2", jp2);

		jPanel.setSize(800, 600);

		JButton btnAddTab = new JButton("Add Tab");


		
		Object column_names[]= {"Transactions"};

		
		model=new DefaultTableModel(column_names, 0);

		
		
	
		
		
		JScrollPane tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		jPanel.add(tablePane);
		

		btnAddTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtp.addTab(txtTabName.getText(), new TransactionTab(mainGUI));
				transactions.add(txtTabName.getText());

				model = new DefaultTableModel(column_names, 0);
				table = new JTable();
				
			}
		});
		
		for (int i = 0; i < transactions.size()-1; i++) {
			System.out.println(i);
			model.addRow(new Object[] { transactions.get(i)});
			model.fireTableDataChanged();

			table.setModel(model);
			table.repaint();
		}

		jPanel.add(btnAddTab, "cell 0 1,alignx left,aligny top");

	
		
		btnDelTab.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {

			        int index = jtp.indexOfTab("Glenn");
			        if (index >= 0) {
			            jtp.removeTabAt(index);
			        }

			    }
		});
		
		jPanel.add(tablePane, "cell 0 2 3 1,grow");
		jPanel.add(btnDelTab, "cell 0 3 3 1,growx,aligny top");
		
		txtTabName = new JTextField();
		jPanel.add(txtTabName, "cell 2 1,grow");
		txtTabName.setColumns(10);
		
		
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