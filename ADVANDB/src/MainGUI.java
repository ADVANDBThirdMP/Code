import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;


public class MainGUI {
	private JPanel leftJPanel = new JPanel();
	private JPanel rightJPanel = new JPanel();	
	private JScrollPane rightScrollPane = new JScrollPane(rightJPanel);
	private JScrollPane leftScrollPane = new JScrollPane(leftJPanel);
	private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, rightScrollPane);
	private JFrame frame = new JFrame("MCO3");	
	private LeftSplit leftSplit;
	private RightSplit rightSplit;
			
	
	public MainGUI() throws Exception{
		
		leftSplit = new LeftSplit(this);
		rightSplit = new RightSplit(this);
		frame.setLayout(new BorderLayout());
		rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		splitPane.setEnabled(true);
		splitPane.setResizeWeight(.2d);
		
		setLeftSplit();
		setRightSplit();
		
		rightJPanel.setLayout(new BorderLayout());
		leftJPanel.setLayout(new BorderLayout());

		
		
        frame.add(splitPane, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);	  
	

		frame.setSize(1500, 650);
		frame.setLocationRelativeTo(null);
	}
	
	
	public void removeLeftSplit(){
		leftJPanel.removeAll();
		frame.revalidate(); // For Java 1.7 or above
		frame.repaint();
	}	
	
	
	public void removeAllRightSplit(){
		rightJPanel.removeAll();
		frame.revalidate(); // For Java 1.7 or above
		frame.repaint();
	}
	   
	public void setRightSplit() {
		rightJPanel.add(rightSplit.getJPanel());
	}
	
	public void removeRightSplit(JPanel guiClass)
	{
		rightJPanel.remove(guiClass);
		frame.revalidate(); // For Java 1.7 or above
		frame.repaint();
	}
	
	public void setLeftSplit()
	{
		leftJPanel.add(leftSplit.getJPanel());
		frame.revalidate(); // For Java 1.7 or above
		frame.repaint();
		leftJPanel.revalidate();
		leftJPanel.repaint();
	}
	
		public void removeAllLeftSplit()
		{
			leftJPanel.removeAll();
			frame.revalidate(); // For Java 1.7 or above
		frame.repaint();
	}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try 
		{ 
		    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
		} 
		catch(Exception e){ 
		}
	MainGUI sp = new MainGUI();
	}


}