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

import model.Lot;
import model.ParkingDatabase;
import model.Space;
import model.Staff;
import model.StaffSpace;

/**
 * A user interface to database, a user can add and update an existing tuple.
 *
 * @author Matthew Chen, Tuan Dinh
 */
public class ParkingGUI extends JFrame implements ActionListener, TableModelListener {

    private static final long serialVersionUID = -6829838052136488913L;
    private JButton mBtnLotList, mBtnLotSearch;
    private JPanel pnlButtons, mPnlContent;
    private ParkingDatabase mDatabase;
    private List<Lot> mLots;
    private String[] mLotColNames = {"lotName",
            "location",
            "capacity",
            "floors"};

    private Object[][] data;
    private JTable mLotTable;
    private JPanel pnlLotSearch;
    private JButton btnLotSearch;
    private JPanel pnlAddLot;
    private JTextField[] txfAddLotField = new JTextField[4];
    private JButton mBtnAddLot, mBtnAddSpace, mBtnAddStaff;
    private JButton mBtnSpaceList, mBtnSpaceSearch;
    private JButton mBtnStaffList, mBtnStaffSearch;

    private JPanel pnlStaffSearch;
    private JTextField txfStaffName;
    private JPanel pnlSpaceSearch;

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
    private JTextField[] txfReverseParkingField = new JTextField[5];
    private JTable mSpaceTable;
    private JTable mStaffTable;
    private String mSpaceType;
	private JTable mStaffSpaceTable;
	private JButton mBtnAddStaffSpace;
	private JTextField[] txfAddStaffSpaceField = new JTextField[2];
	private JButton mBtnCoveredSpace;
    


    /**
     * Creates the frame and components and launches the GUI.
     */
    public ParkingGUI() {
        super("Parking Application");

        mDatabase = new ParkingDatabase();
        try {

            mLots = mDatabase.getLots();

            data = new Object[mLots.size()][mLotColNames.length];
            for (int i = 0; i < mLots.size(); i++) {
                data[i][0] = mLots.get(i).getLotName();
                data[i][1] = mLots.get(i).getLocation();
                data[i][2] = mLots.get(i).getCapacity();
                data[i][3] = mLots.get(i).getFloors();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            return;
        }
        createComponents();
        setVisible(true);
        setSize(700, 500);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
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
//            if (theEvent.getSource() == mLotTable.getModel()) {
//                mDatabase.updateLot(row, columnName, data);
//            } else if (mSpaceTable != null && theEvent.getSource() == mSpaceTable.getModel()) {
//                mDatabase.updateSpace(row, columnName, data);
            if (mStaffTable != null && theEvent.getSource() == mStaffTable.getModel()) {
                mDatabase.updateStaff(row, columnName, data);
            }

        } catch (Exception theException) {
            JOptionPane.showMessageDialog(this, theException.getMessage());
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

        mBtnLotSearch = new JButton("Lot Search");
        mBtnLotSearch.addActionListener(this);

        mBtnSpaceList = new JButton("Space List");
        mBtnSpaceList.addActionListener(this);

        mBtnSpaceSearch = new JButton("Space Search");
        mBtnSpaceSearch.addActionListener(this);

        mBtnStaffList = new JButton("Staff List");
        mBtnStaffList.addActionListener(this);

        mBtnStaffSearch = new JButton("Staff Search");
        mBtnStaffSearch.addActionListener(this);
        
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
//        pnlButtons.add(mBtnStaffSearch);
        pnlButtons.add(mBtnReserveParking);
        pnlButtons.add(mBtnStaffSpaceList);
        add(pnlButtons, BorderLayout.NORTH);

        //List Panel
        mPnlContent = new JPanel();
        mLotTable = new JTable(data, mLotColNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mLotTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(mLotTable);
        mPnlContent.add(scrollPane);
        mLotTable.getModel().addTableModelListener(this);

		//Search Panel
        createPanelLotSearch();
        createPanelSpaceSearch();
        createStaffSearch();

        //Add Panel
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

        
        add(mPnlContent, BorderLayout.CENTER);
        mPnlSouth = new JPanel();
        mPnlSouth.add(pnlAddLot);
        add(mPnlSouth, BorderLayout.SOUTH);


    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
         Object btn = theEvent.getSource();

        if (btn == mBtnLotList) {
            lotListAction();

        } else if (btn == mBtnLotSearch) {
            loadPanel(pnlLotSearch);

        } else if (btn == mBtnAddLot) {
            addLotAction();

        } else if (btn == mBtnSpaceList) {
            spaceListAction();

        } else if (btn == mBtnSpaceSearch) {
            loadPanel(pnlSpaceSearch);

        } else if (btn == mBtnAddSpace) {
            addSpaceAction();

        } else if (btn == mBtnStaffList) {
            staffListAction();

        } else if (btn == mBtnStaffSearch) {
            loadPanel(pnlStaffSearch);

        } else if (btn == mBtnAddStaff) {
            addStaffAction();
        } else if (btn == mBtnReserveParking) {
            showAvailableParking();

        } else if (btn == mBtnStaffSpaceList) {
            staffSpaceListAction();
            
        } else if (btn == mBtnAddStaffSpace) {
        	addStaffSpaceAction();
        } else if (btn == mBtnCoveredSpace) {
        	coveredSpaceListAction();
        } 


    }

    private void coveredSpaceListAction() {
		// TODO Auto-generated method stub
		
	}

	private void addStaffSpaceAction() {
    	try {
            if (Integer.parseInt(txfAddStaffSpaceField[0].getText()) < 0 || 
            		Integer.parseInt(txfAddStaffSpaceField[0].getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Staff number or Space number "
                		+ "cannot be negative");
                for (int i = 0; i < txfAddStaffSpaceField.length; i++) {
                	txfAddStaffSpaceField[i].setText("");
                }
                return;
            }

            StaffSpace staffSpace = new StaffSpace(Integer.parseInt(txfAddStaffSpaceField[0].getText()), 
            		Integer.parseInt(txfAddStaffSpaceField[1].getText()));
            mDatabase.addStaffSpace(staffSpace);
            JOptionPane.showMessageDialog(null, "Added Successfully!");
            for (int i = 0; i < txfAddStaffSpaceField.length; i++) {
            	txfAddStaffSpaceField[i].setText("");
            }
            staffSpaceListAction();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
		
	}

	/**
     * insert a new tuple to the database, space table,
     * and populate the GUI again.
     */
    private void addSpaceAction() {
        try {
            if (Integer.parseInt(txfAddSpaceField[0].getText()) < 1) {
                JOptionPane.showMessageDialog(this, "Space number must be greater than 0");
                for (int i = 0; i < txfAddSpaceField.length; i++) {
                    txfAddSpaceField[i].setText("");
                }
                return;
            }

            Space space = new Space(Integer.parseInt(txfAddSpaceField[0].getText()),
                    mSpaceType, txfAddSpaceField[1].getText());
            mDatabase.addSpace(space);
            JOptionPane.showMessageDialog(null, "Added Successfully!");
            for (int i = 0; i < txfAddSpaceField.length; i++) {
                txfAddSpaceField[i].setText("");
            }
            spaceListAction();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }

    }

    /**
     * insert a new tuple to the database, lot table,
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
            for (int i = 0; i < txfAddLotField.length; i++) {
                txfAddLotField[i].setText("");
            }
            lotListAction();
        } catch (NumberFormatException theException) {
            JOptionPane.showMessageDialog(this, "Capacity and floors must be numbers");
            return;
        } catch (IllegalArgumentException theException) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }
    }

    /**
     * insert a new tuple to the database, lot table,
     * and populate the GUI again.
     */
    private void addStaffAction() {
        try {
            if (Integer.parseInt(txfAddStaffField[0].getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Staff number cannot be negative");
                for (int i = 0; i < txfAddStaffField.length; i++) {
                    txfAddStaffField[i].setText("");
                }
                return;
            }

            Staff staff = new Staff(Integer.parseInt(txfAddStaffField[0].getText()), txfAddStaffField[1].getText(), txfAddStaffField[2].getText());
            mDatabase.addStaff(staff);
            JOptionPane.showMessageDialog(null, "Added Successfully!");
            for (int i = 0; i < txfAddStaffField.length; i++) {
                txfAddStaffField[i].setText("");
            }
            staffListAction();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }

    }

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

    private void createPanelReserveVisitorParking() {
        pnlReserveParking = new JPanel();
        pnlReserveParking.setLayout(new GridLayout(6, 0));
        JLabel[] txfReserveParkingLabel = new JLabel[5];
        String labelNames[] = {"Enter Booking ID: ", "Enter Space Number: ",
                "Staff Number: ", "Vistor License: ", "Data of Visit: "};
        for (int i = 0; i < labelNames.length; i++) {
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
        mSpaceType = "C";
        JRadioButton mRdoBtnUncovered = new JRadioButton("Uncovered");
        mRdoBtnCovered.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mSpaceType = "C";
				System.out.println("mSpaceType = " + mSpaceType);
			}
		});
        mRdoBtnUncovered.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mSpaceType = "U";
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

    private void createStaffSearch() {
        pnlStaffSearch = new JPanel();
        JLabel lblStaffName = new JLabel("Enter Staff Number: ");
        txfStaffName = new JTextField(25);
        JButton btnStaffSearch = new JButton("Search");
        btnStaffSearch.addActionListener(this);
        pnlStaffSearch.add(lblStaffName);
        pnlStaffSearch.add(txfStaffName);
        pnlStaffSearch.add(btnStaffSearch);

    }

    private void createPanelSpaceSearch() {
        pnlSpaceSearch = new JPanel();
        JLabel lblSpaceName = new JLabel("Enter Space Number: ");
        JTextField txfSpaceName = new JTextField(25);
        JButton btnSpaceSearch = new JButton("Search");
        btnSpaceSearch.addActionListener(this);
        pnlSpaceSearch.add(lblSpaceName);
        pnlSpaceSearch.add(txfSpaceName);
        pnlSpaceSearch.add(btnSpaceSearch);
    }

    private void createPanelLotSearch() {
        pnlLotSearch = new JPanel();
        JLabel lblLotName = new JLabel("Enter Lot Name: ");
        JTextField txfLotName = new JTextField(25);
        btnLotSearch = new JButton("Search");
        btnLotSearch.addActionListener(this);
        pnlLotSearch.add(lblLotName);
        pnlLotSearch.add(txfLotName);
        pnlLotSearch.add(btnLotSearch);
    }


    private void showAvailableParking() {
        // TODO show available parking space for a specified staff of the day of visit.
        //need to query how many space have used.

        loadPanel(pnlReserveParking);


    }

    private void loadPanel(JPanel thePanel) {
        mPnlContent.removeAll();
        mPnlSouth.removeAll();
        mPnlContent.add(thePanel);
        mPnlContent.revalidate();
        mPnlSouth.revalidate();
        this.repaint();
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

        data = new Object[mLots.size()][mLotColNames.length];
        for (int i = 0; i < mLots.size(); i++) {
            data[i][0] = mLots.get(i).getLotName();
            data[i][1] = mLots.get(i).getLocation();
            data[i][2] = mLots.get(i).getCapacity();
            data[i][3] = mLots.get(i).getFloors();
        }

        mPnlContent.removeAll();
        mPnlSouth.removeAll();
        mLotTable = new JTable(data, mLotColNames) {
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

        data = new Object[spaces.size()][spaceColNames.length];
        for (int i = 0; i < spaces.size(); i++) {
            data[i][0] = spaces.get(i).getSpaceNumber();
            data[i][1] = spaces.get(i).getSpaceType();
            data[i][2] = spaces.get(i).getLotName();
        }

        mPnlContent.removeAll();
        mPnlSouth.removeAll();
        mSpaceTable = new JTable(data, spaceColNames) {
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
            JOptionPane.showMessageDialog(this, exception.getMessage());
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
	private void staffSpaceListAction() {
        List<StaffSpace> staffSpaceList;
        try {
            staffSpaceList = mDatabase.getStaffSpace();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }
        String[] columnNames = {"staffNumber", "spaceNumber"};
        Object[][] data = new Object[staffSpaceList.size()][columnNames.length];
        for (int i = 0; i < staffSpaceList.size(); i++) {
            data[i][0] = staffSpaceList.get(i).getStaffNumber();
            data[i][1] = staffSpaceList.get(i).getSpaceNumber();
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
