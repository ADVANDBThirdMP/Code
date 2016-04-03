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

	private JTabbedPane jTabbedPane = new JTabbedPane();
	private JButton btnDelTab;
	private JTextField txtTabName;
	private ArrayList<String> transactions = new ArrayList<String>();
	private JTable table = new JTable();
	private DefaultTableModel model;
	private final JButton btnCommit = new JButton("COMMIT");
	private final JButton btnRollback = new JButton("ROLLBACK");

	private JMenuBar jmb = new JMenuBar();

	private JMenu transaction = new JMenu("Transactions");
	private JMenu run = new JMenu("Run");
	private JMenu file = new JMenu("File");

	private JMenuItem startClient = new JMenuItem("Start Client");
	private JMenuItem startServer = new JMenuItem("Start Server");

	private JMenuItem closeAll = new JMenuItem("Close All");
	private JMenuItem newTransaction = new JMenuItem("New Transaction");
	private JMenuItem deleteTransaction = new JMenuItem("Delete Transaction");

	private JMenuItem runSelected = new JMenuItem("Run");
	private JMenuItem runAll = new JMenuItem("Run All");

	private JButton btnAddTab = new JButton("Add Trans");
	
	private Object column_names[] = { "Transactions" };
	private ActListener act = new ActListener();

	public RightSplit(MainGUI mainGUI) {



		this.mainGUI = mainGUI;
		jPanel.setLayout(null);
		jTabbedPane.setBounds(7, 33, 788, 235);
		jPanel.add(jTabbedPane);

		btnDelTab = new JButton("Del Tab");
		btnDelTab.setBounds(7, 570, 431, 23);

		TransactionTab jp1 = new TransactionTab(mainGUI);
	

		jTabbedPane.addTab("Tab1", jp1.getJPanel());

		jPanel.setSize(800, 600);

		btnAddTab.setBounds(10, 279, 190, 23);

		model = new DefaultTableModel(column_names, 0);
		btnCommit.setBounds(448, 279, 347, 173);
		btnCommit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// for the transactions table
		jPanel.add(btnCommit);

		table.setModel(model);

		JScrollPane tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePane.setBounds(7, 313, 431, 246);

		jPanel.add(tablePane);

		transactions.add("1");
		transactions.add("2");

		for (int i = 0; i < transactions.size(); i++) {
			System.out.println(i);

			model.addRow(new Object[] { transactions.get(i) });
			model.fireTableDataChanged();

			table.setModel(model);
			tablePane.repaint();
			jPanel.repaint();
			jPanel.revalidate();
		}

		jPanel.add(btnAddTab);

		btnDelTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				int index = jTabbedPane.indexOfTab("Glenn");
				if (index >= 0) {
					jTabbedPane.removeTabAt(index);
				}

			}
		});

		jPanel.add(tablePane, "cell 0 2 3 1,grow");
		btnRollback.setBounds(448, 469, 347, 23);

		jPanel.add(btnRollback);
		jPanel.add(btnDelTab);

		txtTabName = new JTextField();
		txtTabName.setBounds(210, 279, 228, 23);
		jPanel.add(txtTabName);
		txtTabName.setColumns(10);

		jPanel.setLayout(new BorderLayout());
		jPanel.add(jmb, BorderLayout.NORTH);

		// for file
		file.add(startClient);
		file.add(startServer);

		// for transaction
		transaction.add(closeAll);
		transaction.add(newTransaction);
		transaction.add(deleteTransaction);

		// for run
		run.add(runAll);
		run.add(runSelected);

		jmb.add(file);
		jmb.add(transaction);
		jmb.add(run);

		btnAddTab.addActionListener(act);

	}

	public JPanel getJPanel() {
		return jPanel;
	}

	private class ActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddTab) {
				jTabbedPane.addTab(txtTabName.getText(), new TransactionTab(mainGUI).getJPanel());
				transactions.add(txtTabName.getText());

				model = new DefaultTableModel(column_names, 0);
				
				for (int i = 0; i < transactions.size(); i++) {
					model.addRow(new Object[] { transactions.get(i) });
				}
				table.setModel(model);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}