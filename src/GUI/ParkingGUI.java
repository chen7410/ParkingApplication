package GUI;
import java.awt.BorderLayout;
import java.awt.Component;
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
	private static final JFrame AssignStaffParking = null;
	private JButton mBtnLotList, mBtnLotSearch;
	private JPanel pnlButtons, pnlContent;
	private ParkingDatabase db;
	private List<Lot> mLots;
	private String[] mLotColNames = {"lotName",
            "location",
            "capacity",
            "floors"};
	private List<Object> mSpaces;
	private String[] mSpaceColNames = {"spaceNumber",
            "spaceType",
            "lotName"};
	
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlLotSearch;
	private JLabel lblLotName;
	private JTextField txfLotName;
	private JButton btnLotSearch;
	
	private JPanel pnlAddLot;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton mBtnAddLot, mBtnAddSpace, mBtnAddStaff;
	
	private JButton mBtnSpaceList, mBtnSpaceSearch;
	private JButton mBtnStaffList, mBtnStaffSearch;
	private JPanel pnlStaffSearch;
	private JLabel lblStaffName;
	private JTextField txfStaffName;
	private JButton btnStaffSearch;
	private JPanel pnlSpaceSearch;
	private JButton btnSpaceSearch;
	private JTextField txfSpaceName;
	private JLabel lblSpaceName;
	private JButton mBtnReserveParking;
	private JButton mBtnAssignStaffParking;
	private JPanel pnlAssignStaffParking;
	private JLabel[] txfStaffParkingLabel = new JLabel[2];
	private JTextField[] txfStaffParkingField = new JTextField[2];
	private JButton mBtnAssign;
	private JPanel pnlSouth;
	private JPanel pnlAddSpace;
	private JLabel[] txfAddSpaceLabel = new JLabel[3];
	private JTextField[] txfAddSpaceField = new JTextField[3];
	private JButton btnAddSpace;
	private JPanel pnlAddStaff;
	private JLabel[] txfAddStaffLabel = new JLabel[3];
	private JTextField[] txfAddStaffField = new JTextField[3];
	private JButton btnAddStaff;
	private JPanel pnlReserveParking;
	private JButton btnReserve;
	private JLabel[] txfReserveParkingLabel = new JLabel[5];
	private JTextField[] txfReverseParkingField = new JTextField[5];
	
	
	/**
	 * Creates the frame and components and launches the GUI.
	 */
	public ParkingGUI() {
		super("Parking Application");
		
		db = new ParkingDatabase();
		try
		{
			mLots = db.getLots();
			
			data = new Object[mLots.size()][mLotColNames.length];
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
		setSize(700, 500);
	}
    
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding 
	 * components to each panel.
	 */
	private void createComponents()
	{
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridLayout(2, 4));
		mBtnLotList = new JButton("Lot List");
		mBtnLotList.addActionListener(this);
		
		mBtnLotSearch = new JButton("Lot Search");
		mBtnLotSearch.addActionListener(this);
		
		mBtnSpaceList = new JButton("Space List");
		mBtnSpaceList.addActionListener(this);
		
		mBtnSpaceSearch= new JButton("Space Search");
		mBtnSpaceSearch.addActionListener(this);
		
		mBtnStaffList = new JButton("Staff List");
		mBtnStaffList.addActionListener(this);
		
		mBtnStaffSearch = new JButton("Staff Search");
		mBtnStaffSearch.addActionListener(this);
		
		mBtnReserveParking = new JButton("Reserve Vistor Parking");
		mBtnReserveParking.addActionListener(this);
		
		mBtnAssignStaffParking = new JButton("Assign Staff Parking");
		mBtnAssignStaffParking.addActionListener(this);
		
		pnlButtons.add(mBtnLotList);
		pnlButtons.add(mBtnLotSearch);
		pnlButtons.add(mBtnSpaceList);
		pnlButtons.add(mBtnSpaceSearch);
		pnlButtons.add(mBtnStaffList);
		pnlButtons.add(mBtnStaffSearch);
		pnlButtons.add(mBtnReserveParking);
		pnlButtons.add(mBtnAssignStaffParking);
		add(pnlButtons, BorderLayout.NORTH);
		
		//List Panel
		pnlContent = new JPanel();
		table = new JTable(data, mLotColNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
//		//Search Panel
		createPanelLotSearch();
		createPanelSpaceSearch();
		createStaffSearch();

		
		//Add Panel
		pnlAddLot = new JPanel();
		pnlAddLot.setLayout(new GridLayout(5, 0));
		String labelNames[] = {"Enter Lot Name: ", "Enter Location: ", "Enter Capacity: ", 
				"Enter Floors: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAddLot.add(panel);
		}
		JPanel panel = new JPanel();
		mBtnAddLot = new JButton("Add Lot");
		mBtnAddLot.addActionListener(this);
		panel.add(mBtnAddLot);
		pnlAddLot.add(panel);
//		
		
		createPanelAddSpace();
		createPanelAddStaff();
		
		createPanelReserveVisitorParking();
		createPanelAssignStaffParking();
		
		
		add(pnlContent, BorderLayout.CENTER);
		
		pnlSouth = new JPanel();
		pnlSouth.add(pnlAddLot);
		add(pnlSouth, BorderLayout.SOUTH);
		
		
	}

	private void createPanelAssignStaffParking() {
		pnlAssignStaffParking = new JPanel();
		pnlAssignStaffParking.setLayout(new GridLayout(3, 0));
		String labelNames[] = {"Enter Staff Number: ", "Enter Space Number: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfStaffParkingLabel[i] = new JLabel(labelNames[i]);
			txfStaffParkingField[i] = new JTextField(25);
			panel.add(txfStaffParkingLabel[i]);
			panel.add(txfStaffParkingField[i]);
			pnlAssignStaffParking.add(panel);
		}
//		
		JPanel panel = new JPanel();
		mBtnAssign = new JButton("Assign");
		mBtnAssign.addActionListener(this);
		panel.add(mBtnAssign);
		pnlAssignStaffParking.add(panel);
		
	}

	private void createPanelReserveVisitorParking() {
		pnlReserveParking = new JPanel();
		pnlReserveParking.setLayout(new GridLayout(6, 0));
		String labelNames[] = {"Enter Booking ID: ", "Enter Space Number: ", 
				"Staff Number: ", "Vistor License: ", "Data of Visit: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfReserveParkingLabel[i] = new JLabel(labelNames[i]);
			txfReverseParkingField[i] = new JTextField(25);
			panel.add(txfReserveParkingLabel[i]);
			panel.add(txfReverseParkingField[i]);
			pnlReserveParking.add(panel);
		}
		
		JPanel panel = new JPanel();
		btnReserve = new JButton("Reserve");
		btnReserve.addActionListener(this);
		panel.add(btnReserve);
		pnlReserveParking.add(panel);
		
	}

	private void createPanelAddStaff() {
		pnlAddStaff = new JPanel();
		pnlAddStaff.setLayout(new GridLayout(4, 0));
		String labelNames[] = {"Enter Staff Number: ", "Enter Telephone Ext: ", 
				"Vehicle License Number: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfAddStaffLabel[i] = new JLabel(labelNames[i]);
			txfAddStaffField[i] = new JTextField(25);
			panel.add(txfAddStaffLabel[i]);
			panel.add(txfAddStaffField[i]);
			pnlAddStaff.add(panel);
		}
		
		JPanel panel = new JPanel();
		btnAddStaff = new JButton("Add Staff");
		btnAddStaff.addActionListener(this);
		panel.add(btnAddStaff);
		pnlAddStaff.add(panel);
		
	}

	private void createPanelAddSpace() {
		pnlAddSpace = new JPanel();
		pnlAddSpace.setLayout(new GridLayout(4, 0));
		String labelNames[] = {"Enter Space Number: ", "Enter Space Type: ", "Enter Lot Name: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfAddSpaceLabel [i] = new JLabel(labelNames[i]);
			txfAddSpaceField[i] = new JTextField(25);
			panel.add(txfAddSpaceLabel[i]);
			panel.add(txfAddSpaceField[i]);
			pnlAddSpace.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddSpace = new JButton("Add Space");
		btnAddSpace.addActionListener(this);
		panel.add(btnAddSpace);
		pnlAddSpace.add(panel);
	}

	private void createStaffSearch() {
		pnlStaffSearch = new JPanel();
		lblStaffName = new JLabel("Enter Staff Number: ");
		txfStaffName = new JTextField(25);
		btnStaffSearch = new JButton("Search");
		btnStaffSearch.addActionListener(this);
		pnlStaffSearch.add(lblStaffName);
		pnlStaffSearch.add(txfStaffName);
		pnlStaffSearch.add(btnStaffSearch);
		
	}

	private void createPanelSpaceSearch() {
		pnlSpaceSearch = new JPanel();
		lblSpaceName = new JLabel("Enter Space Number: ");
		txfSpaceName = new JTextField(25);
		btnSpaceSearch = new JButton("Search");
		btnSpaceSearch.addActionListener(this);
		pnlSpaceSearch.add(lblSpaceName);
		pnlSpaceSearch.add(txfSpaceName);
		pnlSpaceSearch.add(btnSpaceSearch);
	}

	private void createPanelLotSearch() {
		pnlLotSearch = new JPanel();
		lblLotName = new JLabel("Enter Lot Name: ");
		txfLotName = new JTextField(25);
		btnLotSearch = new JButton("Search");
		btnLotSearch.addActionListener(this);
		pnlLotSearch.add(lblLotName);
		pnlLotSearch.add(txfLotName);
		pnlLotSearch.add(btnLotSearch);
//		System.out.println("pnlLotSearch");
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
			//TODO update
		} catch (Exception theException) {
			JOptionPane.showMessageDialog(this, theException.getMessage());
			return;
		}
	}

	@Override
	public void actionPerformed(final ActionEvent theEvent) {
		JButton btn = (JButton) theEvent.getSource();
		
		if (btn == mBtnLotList) {
			lotListAction();
			
		} else if (btn == mBtnLotSearch) {
			loadPanel(pnlLotSearch);
			
		} else if (btn == mBtnAddLot) {
			
			
		} else if (btn == mBtnSpaceList) {
			spaceListAction();
			
		} else if (btn == mBtnSpaceSearch) {
			loadPanel(pnlSpaceSearch);
			
		} else if (btn == mBtnAddSpace) {
			
		} else if (btn == mBtnStaffList) {
			staffListAction();
			
		} else if (btn == mBtnStaffSearch) {
			loadPanel(pnlStaffSearch);
			
		} else if (btn == mBtnAddStaff) {
			
		} else if (btn == mBtnReserveParking) {
			showAvailableParking();
			
		} else if (btn == mBtnAssignStaffParking) {
			loadPanel(pnlAssignStaffParking);
		}
		
	}


	private void showAvailableParking() {
		// TODO show available parking space for a specified staff of the day of visit.
		//need to query how many space have used.
		
		loadPanel(pnlReserveParking);
		
		
	}

	private void loadPanel(JPanel thePanel) {
		pnlContent.removeAll();
		pnlSouth.removeAll();
		pnlContent.add(thePanel);
		pnlContent.revalidate();
		pnlSouth.revalidate();
		this.repaint();
	}

	/**
	 * populate the lot table.
	 */
	private void lotListAction() {
		try {
			mLots = db.getLots();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}
		
		data = new Object[mLots.size()][mLotColNames.length];
		for (int i=0; i<mLots.size(); i++) {
			data[i][0] = mLots.get(i).getLotName();
			data[i][1] = mLots.get(i).getLocation();
			data[i][2] = mLots.get(i).getCapacity();
			data[i][3] = mLots.get(i).getFloors();
		}
		
		pnlContent.removeAll();
		pnlSouth.removeAll();
		table = new JTable(data, mLotColNames);
		table.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		pnlContent.revalidate();
		pnlSouth.add(pnlAddLot);
		pnlSouth.revalidate();
		this.repaint();
	}
	
	/**
	 * populate the space table.
	 */
	private void spaceListAction() {
		//TODO change to populate the space table
		try {
			mLots = db.getLots();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}
		
		data = new Object[mLots.size()][mLotColNames.length];
		for (int i=0; i<mLots.size(); i++) {
			data[i][0] = mLots.get(i).getLotName();
			data[i][1] = mLots.get(i).getLocation();
			data[i][2] = mLots.get(i).getCapacity();
			data[i][3] = mLots.get(i).getFloors();
		}
		
		pnlContent.removeAll();
		pnlSouth.removeAll();
		table = new JTable(data, mLotColNames);
		table.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		pnlContent.revalidate();
		pnlSouth.add(pnlAddSpace);
		pnlSouth.revalidate();
		this.repaint();
	}
	
	/**
	 * populate the staff table.
	 */
	private void staffListAction() {
		//TODO change to populate the staff table
		try {
			mLots = db.getLots();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}
		
		data = new Object[mLots.size()][mLotColNames.length];
		for (int i=0; i<mLots.size(); i++) {
			data[i][0] = mLots.get(i).getLotName();
			data[i][1] = mLots.get(i).getLocation();
			data[i][2] = mLots.get(i).getCapacity();
			data[i][3] = mLots.get(i).getFloors();
		}
		
		pnlContent.removeAll();
		pnlSouth.removeAll();
		table = new JTable(data, mLotColNames);
		table.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		pnlContent.revalidate();
		pnlSouth.add(pnlAddStaff);
		pnlSouth.revalidate();
		this.repaint();
	}



}
