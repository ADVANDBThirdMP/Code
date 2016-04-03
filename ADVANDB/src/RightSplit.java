import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;

public class RightSplit implements ActionListener {

	JPanel jPanel = new JPanel();

	private DBConnect db = new DBConnect();
	private MainGUI mainGUI;

	// transactionstable
	private JTabbedPane jTabbedPane = new JTabbedPane();
	private ArrayList<String> transactions = new ArrayList<String>();
	private JTable table = new JTable();
	private Object column_names[] = { "Transactions" };
	private DefaultTableModel model = new DefaultTableModel(column_names, 0);
	private JScrollPane tablePane;

	// buttons
	private final JButton btnCommit = new JButton("COMMIT");
	private final JButton btnRollback = new JButton("ROLLBACK");
	private JButton btnAddTab = new JButton("Add Trans");
	private JButton btnDelTab = new JButton("Del Trans");

	private ActListener act = new ActListener();

	private JTextField txtNewTransactionsName = new JTextField();

	// menus
	private JMenuBar jMenuBar = new JMenuBar();

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

	public RightSplit(MainGUI mainGUI) {
		this.mainGUI = mainGUI;

		jPanel.setLayout(null);
		jPanel.setSize(800, 600);

		// default transaction
		TransactionTab defaultTransaction = new TransactionTab(mainGUI);
		transactions.add("Default");
		jTabbedPane.addTab("Default", defaultTransaction.getJPanel());

		// for the transactions table
		table.setModel(model);
		tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// populate transactions table based on transactions arraylist
		populateTransactionsTable();

		btnAddTab.setBounds(10, 279, 190, 23);
		btnDelTab.setBounds(7, 570, 431, 23);
		jTabbedPane.setBounds(7, 33, 788, 235);
		btnRollback.setBounds(448, 469, 347, 23);
		tablePane.setBounds(7, 313, 431, 246);
		btnCommit.setBounds(448, 279, 347, 173);

		jPanel.add(btnAddTab);

		jPanel.add(btnRollback);
		jPanel.add(btnDelTab);
		jPanel.add(btnCommit);
		jPanel.add(jTabbedPane);
		jPanel.add(tablePane);
		jPanel.add(tablePane, "cell 0 2 3 1,grow");

		txtNewTransactionsName.setBounds(210, 279, 228, 32);
		jPanel.add(txtNewTransactionsName);
		txtNewTransactionsName.setColumns(10);
		jMenuBar.setBounds(0, 0, 800, 21);
		jPanel.add(jMenuBar);

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

		jMenuBar.add(file);
		jMenuBar.add(transaction);
		jMenuBar.add(run);

		btnAddTab.addActionListener(act);
		btnDelTab.addActionListener(act);
		btnCommit.addActionListener(act);

	}

	public JPanel getJPanel() {
		return jPanel;
	}

	//populates/refreshes table based on array list
	public void populateTransactionsTable() {
		model = new DefaultTableModel(column_names, 0);

		for (int i = 0; i < transactions.size(); i++) {
			model.addRow(new Object[] { transactions.get(i) });
		}
		model.fireTableDataChanged();

		table.setModel(model);
		tablePane.repaint();
		jPanel.repaint();
		jPanel.revalidate();
	}


	// adds transactions to arraylist then refreshses table
	public void addTransactions() {
		jTabbedPane.addTab(txtNewTransactionsName.getText(), new TransactionTab(mainGUI).getJPanel());
		transactions.add(txtNewTransactionsName.getText());

		populateTransactionsTable();
	}
	
	//deletes transactions and refreshes table
	public void deleteTransactions(){
		int row = table.getSelectedRow();
		int column = table.getColumnCount();

		int index = jTabbedPane.indexOfTab(table.getValueAt(row, 0).toString());
		if (index >= 0) {
			jTabbedPane.removeTabAt(index);
		}
		transactions.remove(table.getValueAt(row, 0).toString());
		populateTransactionsTable();
	}

	private class ActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddTab) {
				addTransactions();
			}

			if (e.getSource() == btnDelTab) {
				deleteTransactions();
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}