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

/**
 * A user interface to view the movies, add a new movie and to update an existing movie.
 * The list is a table with all the movie information in it. The TableModelListener listens to
 * any changes to the cells to modify the values for reach movie.
 *
 * @author mmuppa
 */
public class ParkingGUI extends JFrame implements ActionListener, TableModelListener {

    private static final long serialVersionUID = -6829838052136488913L;
    private JButton mBtnLotList, mBtnLotSearch;
    private JPanel pnlButtons, pnlContent;
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
    private JButton mBtnAssignStaffParking;
    private JPanel pnlAssignStaffParking;
    private JPanel pnlSouth;
    private JPanel pnlAddSpace;
    private JTextField[] txfAddSpaceField = new JTextField[3];
    private JPanel pnlAddStaff;
    private JTextField[] txfAddStaffField = new JTextField[3];
    private JPanel pnlReserveParking;
    private JButton btnReserve;
    private JTextField[] txfReverseParkingField = new JTextField[5];
    private JTable mSpaceTable;


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
            if (theEvent.getSource() == mLotTable.getModel()) {
                mDatabase.updateLot(row, columnName, data);

            } else if (theEvent.getSource() == mSpaceTable.getModel()) {
                mDatabase.updateSpace(row, columnName, data);
            }

        } catch (Exception theException) {
            JOptionPane.showMessageDialog(this, theException.getMessage());
            return;
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

        mBtnReserveParking = new JButton("Reserve Vistor Parking");
        mBtnReserveParking.addActionListener(this);

        mBtnAssignStaffParking = new JButton("Assign Staff Parking");
        mBtnAssignStaffParking.addActionListener(this);

        pnlButtons.add(mBtnLotList);
        pnlButtons.add(mBtnSpaceList);
        pnlButtons.add(mBtnStaffList);
//        pnlButtons.add(mBtnStaffSearch);
        pnlButtons.add(mBtnReserveParking);
        pnlButtons.add(mBtnAssignStaffParking);
        add(pnlButtons, BorderLayout.NORTH);

        //List Panel
        pnlContent = new JPanel();
        mLotTable = new JTable(data, mLotColNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0);
            }

            ;
        };
        mLotTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(mLotTable);
        pnlContent.add(scrollPane);
        mLotTable.getModel().addTableModelListener(this);

//		//Search Panel
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

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        JButton btn = (JButton) theEvent.getSource();

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

        } else if (btn == mBtnReserveParking) {
            showAvailableParking();

        } else if (btn == mBtnAssignStaffParking) {
            loadPanel(pnlAssignStaffParking);
        }

    }

    /**
     * insert a new tuple to the database, space table,
     * and populate the GUI again.
     */
    private void addSpaceAction() {
        if (Integer.parseInt(txfAddSpaceField[0].getText()) < 1) {
            JOptionPane.showMessageDialog(this, "Space number must be greater than 0");
            for (int i = 0; i < txfAddSpaceField.length; i++) {
                txfAddSpaceField[i].setText("");
            }
            return;
        }

        Space space = new Space(Integer.parseInt(txfAddSpaceField[0].getText()),
                txfAddSpaceField[1].getText(), txfAddSpaceField[2].getText());
        try {
            mDatabase.addSpace(space);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(null, "Added Successfully!");
        for (int i = 0; i < txfAddSpaceField.length; i++) {
            txfAddSpaceField[i].setText("");
        }
        spaceListAction();
    }

    /**
     * insert a new tuple to the database, lot table,
     * and populate the GUI again.
     */
    private void addLotAction() {
        if (Integer.parseInt(txfAddLotField[2].getText()) < 1 ||
                Integer.parseInt(txfAddLotField[3].getText()) < 1) {
            JOptionPane.showMessageDialog(this, "Capacity and floors must be greater than 0");
            for (int i = 0; i < txfAddLotField.length; i++) {
                txfAddLotField[i].setText("");
            }
            return;
        }

        Lot lot = new Lot(txfAddLotField[0].getText(), txfAddLotField[1].getText(),
                Integer.parseInt(txfAddLotField[2].getText()), Integer.parseInt(txfAddLotField[3].getText()));
        try {
            mDatabase.addLot(lot);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(null, "Added Successfully!");
        for (int i = 0; i < txfAddLotField.length; i++) {
            txfAddLotField[i].setText("");
        }
        lotListAction();
    }

    private void createPanelAssignStaffParking() {
        pnlAssignStaffParking = new JPanel();
        pnlAssignStaffParking.setLayout(new GridLayout(3, 0));
        JLabel[] txfStaffParkingLabel = new JLabel[2];
        JTextField[] txfStaffParkingField = new JTextField[2];
        String labelNames[] = {"Enter Staff Number: ", "Enter Space Number: "};
        
        for (int i = 0; i < labelNames.length; i++) {
            JPanel panel = new JPanel();
            txfStaffParkingLabel[i] = new JLabel(labelNames[i]);
            txfStaffParkingField[i] = new JTextField(25);
            panel.add(txfStaffParkingLabel[i]);
            panel.add(txfStaffParkingField[i]);
            pnlAssignStaffParking.add(panel);
        }
//		
        JPanel panel = new JPanel();
        JButton btnAssign = new JButton("Assign");
        btnAssign.addActionListener(this);
        panel.add(btnAssign);
        pnlAssignStaffParking.add(panel);

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
        JLabel[] txfAddSpaceLabel = new JLabel[3];
        String labelNames[] = {"Enter Space Number: ", "Enter Space Type: ", "Enter Lot Name: "};
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
        panel.add(mBtnAddSpace);
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
//		System.out.println("pnlLotSearch");
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

        pnlContent.removeAll();
        pnlSouth.removeAll();
        mLotTable = new JTable(data, mLotColNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0);
            }

            ;
        };
        mLotTable.getTableHeader().setReorderingAllowed(false);
        mLotTable.getModel().addTableModelListener(this);
        JScrollPane scrollPane = new JScrollPane(mLotTable);
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

        pnlContent.removeAll();
        pnlSouth.removeAll();
        mSpaceTable = new JTable(data, spaceColNames);
        mSpaceTable.getTableHeader().setReorderingAllowed(false);
        mSpaceTable.getModel().addTableModelListener(this);
        JScrollPane scrollPane = new JScrollPane(mSpaceTable);
        pnlContent.add(scrollPane);
        pnlContent.revalidate();
        pnlSouth.add(pnlAddSpace);
        this.repaint();
    }

    /**
     * populate the staff table.
     */
    private void staffListAction() {
        List<Staff> staffList;
        try {
            staffList = mDatabase.getStaff();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }
        String[] columnNames = {"staffNumber", "telephoneExt", "vehicleLicenseNumber"};
        data = new Object[staffList.size()][columnNames.length];
        for (int i = 0; i < staffList.size(); i++) {
            data[i][0] = staffList.get(i).getStaffNumber();
            data[i][1] = staffList.get(i).getPhoneExt();
            data[i][2] = staffList.get(i).getLicenseNumber();
        }

        pnlContent.removeAll();
        pnlSouth.removeAll();

        mLotTable = new JTable(data, columnNames);
        mLotTable.getModel().addTableModelListener(this);
        JScrollPane scrollPane = new JScrollPane(mLotTable);
        pnlContent.add(scrollPane);
        pnlContent.revalidate();
        pnlSouth.add(pnlAddStaff);
        pnlSouth.revalidate();
        this.repaint();
    }
}
