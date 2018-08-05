package GUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.Lot;
import model.ParkingDatabase;

/**
 * A user interface to view the movies, add a new movie and to update an existing movie.
 * The list is a table with all the movie information in it. The TableModelListener listens to
 * any changes to the cells to modify the values for reach movie.
 * @author mmuppa
 *
 */
public class ParkingGUI extends JFrame implements ActionListener, TableModelListener
{
	
	private static final long serialVersionUID = -6829838052136488913L;
	private JButton mBtnLotList, mBtnLotSearch, mBtnAddLot;
	private JPanel pnlButtons, pnlContent;
	private ParkingDatabase db;
	private List<Lot> mLots;
	private String[] columnNames = {"lotName",
            "location",
            "capacity",
            "floors"};
	
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddMovie;
	
	
	/**
	 * Creates the frame and components and launches the GUI.
	 */
	public ParkingGUI() {
		super("Movie Store");
		
		db = new ParkingDatabase();
		try
		{
			mLots = db.getLots();
			
			data = new Object[mLots.size()][columnNames.length];
			for (int i=0; i<mLots.size(); i++) {
				data[i][0] = mLots.get(i).getLotName();
				data[i][1] = mLots.get(i).getLocation();
				data[i][2] = mLots.get(i).getCapacity();
				data[i][3] = mLots.get(i).getFloors();
				
			}
			
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this,"Error: " + e.getMessage());
			return;
		}
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}
    
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding 
	 * components to each panel.
	 */
	private void createComponents()
	{
		pnlButtons = new JPanel();
		mBtnLotList = new JButton("Lot List");
		mBtnLotList.addActionListener(this);
		
		mBtnLotSearch = new JButton("Lot Search");
		mBtnLotSearch.addActionListener(this);
		
		mBtnAddLot = new JButton("Add Lot");
		mBtnAddLot.addActionListener(this);
		
		pnlButtons.add(mBtnLotList);
		pnlButtons.add(mBtnLotSearch);
		pnlButtons.add(mBtnAddLot);
		add(pnlButtons, BorderLayout.NORTH);
		
		//List Panel
		pnlContent = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
//		//Search Panel
//		pnlSearch = new JPanel();
//		lblTitle = new JLabel("Enter Title: ");
//		txfTitle = new JTextField(25);
//		btnTitleSearch = new JButton("Search");
//		btnTitleSearch.addActionListener(this);
//		pnlSearch.add(lblTitle);
//		pnlSearch.add(txfTitle);
//		pnlSearch.add(btnTitleSearch);
//		
		//Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = {"Enter Lot Name: ", "Enter Location: ", "Enter Capacity: ", 
				"Enter Floors: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddMovie = new JButton("Add");
		btnAddMovie.addActionListener(this);
		panel.add(btnAddMovie);
		pnlAdd.add(panel);
//		
		add(pnlContent, BorderLayout.CENTER);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ParkingGUI parkingGUI = new ParkingGUI();
		parkingGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void tableChanged(TableModelEvent theEvent) {
		int row = theEvent.getFirstRow();
		int col = theEvent.getColumn();
		TableModel model = (TableModel)theEvent.getSource();
		String columnName = model.getColumnName(col);
		Object data = model.getValueAt(row, col);
		try {
			
		} catch (Exception theException) {
			JOptionPane.showMessageDialog(this, theException.getMessage());
			return;
		}
		
	}

	@Override
	public void actionPerformed(final ActionEvent theEvent) {
		JButton btn = (JButton) theEvent.getSource();
		
		if (btn == mBtnLotList) {
			
		} else if (btn == mBtnLotSearch) {
			
		} else if (btn == mBtnAddLot) {
//			pnlContent.removeAll();
//			pnlContent.add(pnlAdd);
//			pnlContent.revalidate();
//			this.repaint();
		}
	}



}
