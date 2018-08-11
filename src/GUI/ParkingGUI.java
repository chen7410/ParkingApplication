package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.*;

/**
 * A user interface to database, a user can add and update an existing tuple.
 *
 * @author Matthew Chen, Tuan Dinh
 */
public class ParkingGUI extends JFrame implements ActionListener, TableModelListener {

	private static final long serialVersionUID = -6829838052136488913L;
	private JButton mBtnLotList;
	private JPanel pnlButtons, mPnlContent;
	private ParkingDatabase mDatabase;
	private List<Lot> mLots;
	private String[] mLotColNames = {"lotName",
			"location",
			"capacity",
	"floors"};

	private Object[][] mData;
	private JTable mLotTable;
	private JPanel pnlAddLot;
	private JTextField[] txfAddLotField = new JTextField[4];
	private JButton mBtnAddLot, mBtnAddSpace, mBtnAddStaff;
	private JButton mBtnSpaceList;
	private JButton mBtnStaffList;

	private JButton mBtnReserveParking;
	private JButton mBtnStaffSpaceList;
	private JPanel pnlAddStaffSpace;
	private JPanel mPnlSouth;
	private JPanel pnlAddSpace;
	private JTextField[] txfAddSpaceField = new JTextField[2];
	private JPanel pnlAddStaff;
	private JTextField[] txfAddStaffField = new JTextField[3];
	private JPanel pnlReserveParking;
	private JButton btnReserve;
	private JTextField[] txfReverseParkingField = new JTextField[4];
	private JTable mSpaceTable;
	private JTable mStaffTable;
	private String mSpaceType;
	private JTable mStaffSpaceTable;
	private JTable mSpaceBookingTable;
	private JButton mBtnAddStaffSpace;
	private JTextField[] txfAddStaffSpaceField = new JTextField[2];
	private JButton mBtnCoveredSpace;
	private JPanel pnlAddCoveredSpace;
	private JTextField[] txfAddCoveredSpaceField = new JTextField[2];
	private JButton mBtnAddCoveredSpace;
	private JTable mCoveredSpaceTable;
	private List<CoveredSpace> staffSpaceList;
	private List<SpaceBooking> bookingList;


	/**
	 * Creates the frame and components and launches the GUI.
	 */
	public ParkingGUI() {
		super("Parking Application");

		mDatabase = new ParkingDatabase();
		try {

			mLots = mDatabase.getLots();

			mData = new Object[mLots.size()][mLotColNames.length];
			for (int i = 0; i < mLots.size(); i++) {
				mData[i][0] = mLots.get(i).getLotName();
				mData[i][1] = mLots.get(i).getLocation();
				mData[i][2] = mLots.get(i).getCapacity();
				mData[i][3] = mLots.get(i).getFloors();

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
			return;
		}
		createComponents();
		setVisible(true);
		setSize(700, 700);
	}

	/**
	 * Initialize the GUI.
	 * @param theArguments
	 */
	public static void main(String[] theArguments) {
		ParkingGUI parkingGUI = new ParkingGUI();
		parkingGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void tableChanged(TableModelEvent theEvent) {
		int row = theEvent.getFirstRow();
		int col = theEvent.getColumn();
		TableModel model = (TableModel) theEvent.getSource();
		String columnName = model.getColumnName(col);
		Object data = model.getValueAt(row, col);
		try {
			if (mStaffTable != null && theEvent.getSource() == mStaffTable.getModel()) {
				mDatabase.updateStaff(row, columnName, data);
			}

		} catch (Exception theException) {
			JOptionPane.showMessageDialog(this, theException);
		}
	}


	/**
	 * Creates panels for all database tables and adds the corresponding
	 * components to each panel.
	 */
	@SuppressWarnings("serial")
	private void createComponents() {
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridLayout(2, 4));
		mBtnLotList = new JButton("Lot List");
		mBtnLotList.addActionListener(this);

		mBtnSpaceList = new JButton("Space List");
		mBtnSpaceList.addActionListener(this);

		mBtnStaffList = new JButton("Staff List");
		mBtnStaffList.addActionListener(this);

		mBtnCoveredSpace = new JButton("Covered Space List");
		mBtnCoveredSpace.addActionListener(this);

		mBtnReserveParking = new JButton("Reserve Vistor Parking");
		mBtnReserveParking.addActionListener(this);

		mBtnStaffSpaceList = new JButton("Assign Staff Parking");
		mBtnStaffSpaceList.addActionListener(this);

		pnlButtons.add(mBtnLotList);
		pnlButtons.add(mBtnSpaceList);
		pnlButtons.add(mBtnStaffList);
		pnlButtons.add(mBtnCoveredSpace);
		pnlButtons.add(mBtnReserveParking);
		pnlButtons.add(mBtnStaffSpaceList);
		add(pnlButtons, BorderLayout.NORTH);

		//List Panel
		mPnlContent = new JPanel();
		mLotTable = new JTable(mData, mLotColNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mLotTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(mLotTable);
		mPnlContent.add(scrollPane);
		mLotTable.getModel().addTableModelListener(this);

		//Add Panels
		pnlAddLot = new JPanel();
		pnlAddLot.setLayout(new GridLayout(5, 0));
		String labelNames[] = {"Enter Lot Name: ", "Enter Location: ", "Enter Capacity: ",
		"Enter Floors: "};
		JLabel[] txfLabel = new JLabel[4];
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfAddLotField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfAddLotField[i]);
			pnlAddLot.add(panel);
		}
		JPanel panel = new JPanel();
		mBtnAddLot = new JButton("Add Lot");
		mBtnAddLot.addActionListener(this);
		panel.add(mBtnAddLot);
		pnlAddLot.add(panel);

		createPanelAddSpace();
		createPanelAddStaff();
		createPanelReserveVisitorParking();
		createPanelAddStaffSpace();
		createPanelAddCoveredSpace();


		add(mPnlContent, BorderLayout.CENTER);
		mPnlSouth = new JPanel();
		mPnlSouth.add(pnlAddLot);
		add(mPnlSouth, BorderLayout.SOUTH);


	}

	/**
	 * the actions trigger by buttons.
	 */
	@Override
	public void actionPerformed(final ActionEvent theEvent) {
		Object btn = theEvent.getSource();

		if (btn == mBtnLotList) {
			lotListAction();

		} else if (btn == mBtnAddLot) {
			addLotAction();

		} else if (btn == mBtnSpaceList) {
			spaceListAction();

		} else if (btn == mBtnAddSpace) {
			addSpaceAction();

		} else if (btn == mBtnStaffList) {
			staffListAction();

		} else if (btn == mBtnAddStaff) {
			addStaffAction();

		} else if (btn == mBtnReserveParking) {
			spaceBookingAction();

		} else if (btn == mBtnStaffSpaceList) {
			staffSpaceListAction();

		} else if (btn == mBtnAddStaffSpace) {
			addStaffSpaceAction();
		} else if (btn == mBtnCoveredSpace) {
			coveredSpaceListAction();

		} else if (btn == mBtnAddCoveredSpace) {
			addCoveredSpaceAction();

		} else if (btn == btnReserve) {
			reserveParkingAction();
		}
	}

	/**
	 * Reserve/add a parking space for a visitor
	 * and populate the GUI again.
	 * @throws NumberFormatException 
	 */
	private void reserveParkingAction() {
		try {
			if (Integer.parseInt(txfReverseParkingField[0].getText()) < 0 || 
					Integer.parseInt(txfReverseParkingField[1].getText()) < 0) {
				JOptionPane.showMessageDialog(this, "Staff number or Space number "
						+ "cannot be negative");

				for (final JTextField aTxfAddStaffSpaceField : txfReverseParkingField) {
					aTxfAddStaffSpaceField.setText("");
				}
				return;
			}
			int space = Integer.parseInt(txfReverseParkingField[0].getText());
			String date = txfReverseParkingField[0].getText();
			boolean check = false;
			
			//check space number and date
			for (SpaceBooking coveredSpace : bookingList) {
				check = check || space == coveredSpace.getSpaceNumber();
				check = check || date == coveredSpace.getVisitorDate();
			}

			if (!check) {
				JOptionPane.showMessageDialog(this, "This space is not avaliable");
				return;
			}

			SpaceBooking staffSpace = new SpaceBooking(0, Integer.parseInt(txfReverseParkingField[0].getText()), 
					Integer.parseInt(txfReverseParkingField[1].getText()), txfReverseParkingField[2].getText(),
					txfReverseParkingField[3].getText());
			mDatabase.addSpaceBooking(staffSpace);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddStaffSpaceField : txfReverseParkingField) {
				aTxfAddStaffSpaceField.setText("");
			}
			spaceBookingAction();
		} catch (final NumberFormatException theException) {
			JOptionPane.showMessageDialog(this, "Staff number or Space number must be numbers");
		} catch (final Exception theException) {
			JOptionPane.showMessageDialog(this, theException);
		}

	}

	/**
	 * populate the coveredSoace table to GUI.
	 * @throws Exception if fail to fetch data from the database.
	 */
	@SuppressWarnings("serial")
	private void coveredSpaceListAction() {
		final String[] coveredSpaceColNames = {"spaceNumber", "monthlyRate"};
		List<CoveredSpace> coveredSpaces;
		try {
			coveredSpaces = mDatabase.getCoveredSpaces();
		} catch (final Exception theException) {
			JOptionPane.showMessageDialog(this, theException.getMessage());
			return;
		}

		mData = new Object[coveredSpaces.size()][coveredSpaceColNames.length];
		for (int i = 0; i < coveredSpaces.size(); i++) {
			mData[i][0] = coveredSpaces.get(i).getSpaceNumber();
			mData[i][1] = coveredSpaces.get(i).getMonthlyRate();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();
		mCoveredSpaceTable = new JTable(mData, coveredSpaceColNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mCoveredSpaceTable.getTableHeader().setReorderingAllowed(false);
		mCoveredSpaceTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mCoveredSpaceTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlAddCoveredSpace);
		this.repaint();

	}

	/**
	 * Insert a StaffSpace to the database table StaffSpace.
	 * The Space must not be taken, and populate the GUI again.
	 * @throws NumberFormatException
	 */
	private void addStaffSpaceAction() {
		try {
			if (Integer.parseInt(txfAddStaffSpaceField[0].getText()) < 0 || 
					Integer.parseInt(txfAddStaffSpaceField[1].getText()) < 0) {
				JOptionPane.showMessageDialog(this, "Staff number or Space number "
						+ "cannot be negative");

				for (final JTextField aTxfAddStaffSpaceField : txfAddStaffSpaceField) {
					aTxfAddStaffSpaceField.setText("");
				}
				return;
			}
			int space = Integer.parseInt(txfAddStaffSpaceField[1].getText());
			boolean check = false;
			for (CoveredSpace coveredSpace : staffSpaceList) {
				check = check || space == coveredSpace.getSpaceNumber();
			}

			if (!check) {
				JOptionPane.showMessageDialog(this, "This space is not avaliable for staffs");
				return;
			}


			StaffSpace staffSpace = new StaffSpace(Integer.parseInt(txfAddStaffSpaceField[0].getText()), 
					Integer.parseInt(txfAddStaffSpaceField[1].getText()));
			mDatabase.addStaffSpace(staffSpace);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddStaffSpaceField : txfAddStaffSpaceField) {
				aTxfAddStaffSpaceField.setText("");
			}
			staffSpaceListAction();
		} catch (final NumberFormatException theException) {
			JOptionPane.showMessageDialog(this, "Staff number or Space number must be numbers");
		} catch (final Exception theException) {
			JOptionPane.showMessageDialog(this, theException);
		}

	}

	/**
	 * insert a new Space to the database table Space,
	 * and populate the GUI again.
	 */
	private void addSpaceAction() {
		try {
			if (Integer.parseInt(txfAddSpaceField[0].getText()) < 1) {
				JOptionPane.showMessageDialog(this, "Space number must be greater than 0");
				for (final JTextField aTxfAddSpaceField : txfAddSpaceField) {
					aTxfAddSpaceField.setText("");
				}
				return;
			}

			Space space = new Space(Integer.parseInt(txfAddSpaceField[0].getText()),
					mSpaceType, txfAddSpaceField[1].getText());
			mDatabase.addSpace(space);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddSpaceField : txfAddSpaceField) {
				aTxfAddSpaceField.setText("");
			}
			spaceListAction();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, "Invalid input.");
		}

	}

	/**
	 * insert a new lot to the database table lot,
	 * and populate the GUI again.
	 */
	private void addLotAction() {
		try {
			if (Integer.parseInt(txfAddLotField[2].getText()) < 1 ||
					Integer.parseInt(txfAddLotField[3].getText()) < 1) {
				JOptionPane.showMessageDialog(this, "Capacity and floors must be greater than 0");
				return;
			}
			Lot lot = new Lot(txfAddLotField[0].getText(), txfAddLotField[1].getText(),
					Integer.parseInt(txfAddLotField[2].getText()), Integer.parseInt(txfAddLotField[3].getText()));
			mDatabase.addLot(lot);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddLotField : txfAddLotField) {
				aTxfAddLotField.setText("");
			}
			lotListAction();
		} catch (final NumberFormatException theException) {
			JOptionPane.showMessageDialog(this, "Capacity and floors must be numbers");
		} catch (IllegalArgumentException theException) {
			JOptionPane.showMessageDialog(this, "Invalid input");
		} catch (final Exception theException) {
			JOptionPane.showMessageDialog(this, theException.toString());
		}
	}

	/**
	 * insert a new staff to the database table Staff,
	 * and populate the GUI again.
	 */
	private void addStaffAction() {
		try {
			if (Integer.parseInt(txfAddStaffField[0].getText()) < 0) {
				JOptionPane.showMessageDialog(this, "Staff number cannot be negative");
				for (final JTextField aTxfAddStaffField : txfAddStaffField) {
					aTxfAddStaffField.setText("");
				}
				return;
			}

			Staff staff = new Staff(Integer.parseInt(txfAddStaffField[0].getText()), txfAddStaffField[1].getText(), txfAddStaffField[2].getText());
			mDatabase.addStaff(staff);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddStaffField : txfAddStaffField) {
				aTxfAddStaffField.setText("");
			}
			staffListAction();
		} catch (final NumberFormatException theException) {
			JOptionPane.showMessageDialog(this, "Staff number must be a number");
		} catch (final Exception theException) {
			JOptionPane.showMessageDialog(this, theException.toString());
		}

	}

	/**
	 * insert a new covered space to the database table CoveredSpace,
	 * and populate the GUI again.
	 */
	private void addCoveredSpaceAction() {
		try {
			System.out.println(txfAddCoveredSpaceField[0].getText().toString());
			if (Integer.parseInt(txfAddCoveredSpaceField[0].getText()) < 1 ||
					Float.parseFloat(txfAddCoveredSpaceField[1].getText()) < 0) {

				JOptionPane.showMessageDialog(this, "Space number and monthly rate must be greater than 0");
				for (final JTextField aTxfAddCoveredSpaceField : txfAddCoveredSpaceField) {
					aTxfAddCoveredSpaceField.setText("");
				}
				return;
			}

			CoveredSpace coveredSpace = new CoveredSpace(Integer.parseInt(txfAddCoveredSpaceField[0].getText()),
					Float.parseFloat(txfAddCoveredSpaceField[1].getText()));
			mDatabase.addCoveredSpace(coveredSpace);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (final JTextField aTxfAddCoveredSpaceField : txfAddCoveredSpaceField) {
				aTxfAddCoveredSpaceField.setText("");
			}
			coveredSpaceListAction();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, "Invalid input.");
		}

	}
	
	/**
	 * create the panel to add staff space.
	 */
	private void createPanelAddStaffSpace() {
		pnlAddStaffSpace = new JPanel();
		pnlAddStaffSpace.setLayout(new GridLayout(3, 0));
		JLabel[] txfStaffSpaceLabel = new JLabel[2];

		String labelNames[] = {"Enter Staff Number: ", "Enter Space Number: "};

		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfStaffSpaceLabel[i] = new JLabel(labelNames[i]);
			txfAddStaffSpaceField[i] = new JTextField(25);
			panel.add(txfStaffSpaceLabel[i]);
			panel.add(txfAddStaffSpaceField[i]);
			pnlAddStaffSpace.add(panel);
		}

		JPanel panel = new JPanel();
		mBtnAddStaffSpace = new JButton("Assign");
		mBtnAddStaffSpace.addActionListener(this);
		panel.add(mBtnAddStaffSpace);
		pnlAddStaffSpace.add(panel);

	}

	/**
	 * create the panel to reserve visitor parking.
	 */
	private void createPanelReserveVisitorParking() {
		pnlReserveParking = new JPanel();
		pnlReserveParking.setLayout(new GridLayout(5, 0));
		JLabel[] txfReserveParkingLabel = new JLabel[4];
		String labelNames[] = {"Enter Space Number: ",
				"Staff Number: ", "Vistor License: ", "Data of Visit (YYYY-MM-DD): "};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfReserveParkingLabel[i] = new JLabel(labelNames[i]);
			panel.add(txfReserveParkingLabel[i]);
			txfReverseParkingField[i] = new JTextField(25);
			panel.add(txfReverseParkingField[i]);
			pnlReserveParking.add(panel);
		}

		JPanel panel = new JPanel();
		btnReserve = new JButton("Reserve");
		btnReserve.addActionListener(this);
		panel.add(btnReserve);
		pnlReserveParking.add(panel);

	}

	/**
	 * create the panel to add staff.
	 */
	private void createPanelAddStaff() {
		pnlAddStaff = new JPanel();
		pnlAddStaff.setLayout(new GridLayout(4, 0));
		JLabel[] txfAddStaffLabel = new JLabel[3];
		String labelNames[] = {"Enter Staff Number: ", "Enter Telephone Ext: ",
		"Vehicle License Number: "};

		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfAddStaffLabel[i] = new JLabel(labelNames[i]);
			txfAddStaffField[i] = new JTextField(25);
			panel.add(txfAddStaffLabel[i]);
			panel.add(txfAddStaffField[i]);
			pnlAddStaff.add(panel);
		}

		JPanel panel = new JPanel();
		mBtnAddStaff = new JButton("Add Staff");
		mBtnAddStaff.addActionListener(this);
		panel.add(mBtnAddStaff);
		pnlAddStaff.add(panel);

	}

	/**
	 * create the panel to add covered space.
	 */
	private void createPanelAddCoveredSpace() {
		pnlAddCoveredSpace = new JPanel();
		pnlAddCoveredSpace.setLayout(new GridLayout(4, 0));
		JLabel[] txfAddCoveredSpaceLabel = new JLabel[3];
		String labelNames[] = {"Enter Space Number: ", "Enter Monthly Rate: "};

		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfAddCoveredSpaceLabel[i] = new JLabel(labelNames[i]);
			txfAddCoveredSpaceField[i] = new JTextField(25);
			panel.add(txfAddCoveredSpaceLabel[i]);
			panel.add(txfAddCoveredSpaceField[i]);
			pnlAddCoveredSpace.add(panel);
		}

		JPanel panel = new JPanel();
		mBtnAddCoveredSpace = new JButton("Add Covered Space");
		mBtnAddCoveredSpace.addActionListener(this);
		panel.add(mBtnAddCoveredSpace);
		pnlAddCoveredSpace.add(panel);

	}

	/**
	 * create the panel to add space.
	 */
	private void createPanelAddSpace() {
		pnlAddSpace = new JPanel();
		pnlAddSpace.setLayout(new GridLayout(4, 0));
		JLabel[] txfAddSpaceLabel = new JLabel[2];
		String labelNames[] = {"Enter Space Number: ", "Enter Lot Name: "};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfAddSpaceLabel[i] = new JLabel(labelNames[i]);
			txfAddSpaceField[i] = new JTextField(25);
			panel.add(txfAddSpaceLabel[i]);
			panel.add(txfAddSpaceField[i]);
			pnlAddSpace.add(panel);
		}
		JPanel panel = new JPanel();
		mBtnAddSpace = new JButton("Add Space");
		mBtnAddSpace.addActionListener(this);

		JRadioButton mRdoBtnCovered = new JRadioButton("Covered");
		mRdoBtnCovered.setSelected(true);
		mSpaceType = "Covered";
		JRadioButton mRdoBtnUncovered = new JRadioButton("Uncovered");
		mRdoBtnCovered.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mSpaceType = "Covered";
				System.out.println("mSpaceType = " + mSpaceType);
			}
		});
		mRdoBtnUncovered.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mSpaceType = "Uncovered";
				System.out.println("mSpaceType = " + mSpaceType);
			}
		});
		ButtonGroup group = new ButtonGroup();

		group.add(mRdoBtnCovered);
		group.add(mRdoBtnUncovered);
		panel.add(mBtnAddSpace);
		JPanel radioPnl = new JPanel();
		radioPnl.add(mRdoBtnCovered);
		radioPnl.add(mRdoBtnUncovered);
		pnlAddSpace.add(radioPnl);
		pnlAddSpace.add(panel);
	}

	/**
	 * populate the lot table.
	 */
	@SuppressWarnings("serial")
	private void lotListAction() {
		try {
			mLots = mDatabase.getLots();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}

		mData = new Object[mLots.size()][mLotColNames.length];
		for (int i = 0; i < mLots.size(); i++) {
			mData[i][0] = mLots.get(i).getLotName();
			mData[i][1] = mLots.get(i).getLocation();
			mData[i][2] = mLots.get(i).getCapacity();
			mData[i][3] = mLots.get(i).getFloors();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();
		mLotTable = new JTable(mData, mLotColNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			;
		};
		mLotTable.getTableHeader().setReorderingAllowed(false);
		mLotTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mLotTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlAddLot);
		mPnlSouth.revalidate();
		this.repaint();
	}

	/**
	 * populate the space table.
	 */
	@SuppressWarnings("serial")
	private void spaceListAction() {
		final String[] spaceColNames = {"spaceNumber",
				"spaceType",
		"lotName"};
		List<Space> spaces;
		try {
			spaces = mDatabase.getSpaces();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}

		mData = new Object[spaces.size()][spaceColNames.length];
		for (int i = 0; i < spaces.size(); i++) {
			mData[i][0] = spaces.get(i).getSpaceNumber();
			mData[i][1] = spaces.get(i).getSpaceType();
			mData[i][2] = spaces.get(i).getLotName();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();
		mSpaceTable = new JTable(mData, spaceColNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mSpaceTable.getTableHeader().setReorderingAllowed(false);
		mSpaceTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mSpaceTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlAddSpace);
		this.repaint();
	}

	/**
	 * populate the staff table.
	 */
	@SuppressWarnings("serial")
	private void staffListAction() {
		List<Staff> staffList;
		try {
			staffList = mDatabase.getStaff();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception);
			return;
		}
		String[] columnNames = {"staffNumber", "telephoneExt", "vehicleLicenseNumber"};
		Object[][] data = new Object[staffList.size()][columnNames.length];
		for (int i = 0; i < staffList.size(); i++) {
			data[i][0] = staffList.get(i).getStaffNumber();
			data[i][1] = staffList.get(i).getPhoneExt();
			data[i][2] = staffList.get(i).getLicenseNumber();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();

		mStaffTable = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return !(column == 0);
			}
		};
		mStaffTable.getTableHeader().setReorderingAllowed(false);
		mStaffTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mStaffTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlAddStaff);
		mPnlSouth.revalidate();
		this.repaint();
	}

	/**
	 * populate the staff table.
	 */
	@SuppressWarnings("serial")
	private void spaceBookingAction() {

		try {
			bookingList = mDatabase.getSpaceBooking();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception);
			return;
		}
		String[] columnNames = {"spaceNumber", "dateOfVisit", "bookingID", "staffNumber", "visitorLicense"};
		Object[][] data = new Object[bookingList.size()][columnNames.length];
		for (int i = 0; i < bookingList.size(); i++) {
			data[i][0] = bookingList.get(i).getSpaceNumber();
			data[i][1] = bookingList.get(i).getVisitorDate();
			data[i][2] = bookingList.get(i).getBookingID();
			data[i][3] = bookingList.get(i).getStaffNumber();
			data[i][4] = bookingList.get(i).getVisitorLicense();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();

		mSpaceBookingTable = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mSpaceBookingTable.getTableHeader().setReorderingAllowed(false);
		mSpaceBookingTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mSpaceBookingTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlReserveParking);
		mPnlSouth.revalidate();
		this.repaint();
	}

	/**
	 * populate the staff table.
	 */
	@SuppressWarnings("serial")
	private void staffSpaceListAction() {

		try {
			staffSpaceList = mDatabase.getStaffSpace();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage());
			return;
		}
		String[] columnNames = {"spaceNumber", "monthlyRate"};
		Object[][] data = new Object[staffSpaceList.size()][columnNames.length];
		for (int i = 0; i < staffSpaceList.size(); i++) {
			data[i][0] = staffSpaceList.get(i).getSpaceNumber();
			data[i][1] = staffSpaceList.get(i).getMonthlyRate();
		}

		mPnlContent.removeAll();
		mPnlSouth.removeAll();

		mStaffSpaceTable = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		mStaffSpaceTable.getTableHeader().setReorderingAllowed(false);
		mStaffSpaceTable.getModel().addTableModelListener(this);
		JScrollPane scrollPane = new JScrollPane(mStaffSpaceTable);
		mPnlContent.add(scrollPane);
		mPnlContent.revalidate();
		mPnlSouth.add(pnlAddStaffSpace);
		mPnlSouth.revalidate();
		this.repaint();
	}
}
