import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class MainOptionsPanel extends JPanel {

	JPanel buttonPanel;
	JPanel displayPanel;
	JPanel calendarPanel;
	JPanel addEmployeePanel;
	JPanel removeEmployeePanel;
	MyTableModel customTableModel;

	public MainOptionsPanel(){
		customTableModel = new MyTableModel();
		
		

		//Instantiate Panels
		buttonPanel = new JPanel();
		displayPanel = new JPanel();
		addEmployeePanel = new JPanel();
		removeEmployeePanel = new JPanel();
		calendarPanel = new JPanel();

		// Set layout
		this.setLayout(new BorderLayout());
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		displayPanel.setLayout(new BorderLayout());
		addEmployeePanel.setLayout(new GridLayout(0,1));
		addEmployeePanel.setMaximumSize(new Dimension(300, 400));
		
		removeEmployeePanel.setLayout(new GridLayout(0,1));
		removeEmployeePanel.setMaximumSize(new Dimension(200, 200));

		// Set Up Sub-Panels
		setUpButtonPanel();
		setUpDisplayPanel();
		setUpAddEmployeePanel();
		setUpRemoveEmployeePanel();
		setUpCalendarPanel();

		// Add Sub-Panels to mainPanel
		this.add(buttonPanel, BorderLayout.LINE_START);
//		this.add(displayPanel, BorderLayout.CENTER);
		this.add(new CalendarPanel(), BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(400, 400));

	}

	public void setUpButtonPanel(){

		// Initialize Components
		JButton addEmployeeButton = new JButton ("Add Employee");
		JButton removeEmployeeButton = new JButton("Remove Employee");
		JButton getAllEmployeeDetails = new JButton("Get All Employee Details");
		JButton displayCalender = new JButton("Display Calender"); // For Future Update

		// Add Components to the Button Panel
		buttonPanel.add(addEmployeeButton);
		buttonPanel.add(removeEmployeeButton);
		buttonPanel.add(getAllEmployeeDetails);
		buttonPanel.add(displayCalender); 



		//Action Listeners
		addEmployeeButton.addActionListener(new ActionListener() {
			boolean panelShowing = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				panelShowing = !panelShowing;
				if(panelShowing){		
					buttonPanel.add(addEmployeePanel);
					buttonPanel.repaint();
					buttonPanel.revalidate();
				}else{
					buttonPanel.remove(addEmployeePanel);
					buttonPanel.repaint();
					buttonPanel.revalidate();
				}
			}
		});

		removeEmployeeButton.addActionListener(new ActionListener() {
			boolean panelShowing = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				panelShowing = !panelShowing;
				if(panelShowing){
					buttonPanel.add(removeEmployeePanel);
					buttonPanel.repaint();
					buttonPanel.revalidate();

				}else{
					buttonPanel.remove(removeEmployeePanel);
					buttonPanel.repaint();
					buttonPanel.revalidate();

				}

			}
		});

	}

	public void setUpDisplayPanel(){

		JTable userDataTable = new JTable(customTableModel);
		userDataTable.setCellSelectionEnabled(true);

		JScrollPane scrollPane = new JScrollPane(userDataTable);
		displayPanel.add(scrollPane, BorderLayout.CENTER);
	}

	public void setUpAddEmployeePanel(){
		JLabel idLabel = new JLabel("Employee ID");
		JLabel firstNameLabel = new JLabel("First Name");
		JLabel lastNameLabel = new JLabel("Last Name");
		JLabel phoneNumberLabel = new JLabel("Phone Number");
		JLabel emailLabel = new JLabel("Email");

		final JTextField idField = new JTextField();
		final JTextField firstNameField = new JTextField();
		final JTextField lastNameField = new JTextField();
		final JTextField phoneNumberField = new JTextField();
		final JTextField emailField = new JTextField();

		JButton addEmployeeButton = new JButton("Submit");

		addEmployeePanel.add(idLabel);
		addEmployeePanel.add(idField);
		addEmployeePanel.add(firstNameLabel);
		addEmployeePanel.add(firstNameField);
		addEmployeePanel.add(lastNameLabel);
		addEmployeePanel.add(lastNameField);
		addEmployeePanel.add(phoneNumberLabel);
		addEmployeePanel.add(phoneNumberField);
		addEmployeePanel.add(emailLabel);
		addEmployeePanel.add(emailField);
		addEmployeePanel.add(addEmployeeButton);

		addEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int employeeID = Integer.parseInt(idField.getText());
				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String phoneNumber = phoneNumberField.getText();
				String email = emailField.getText();
				int employeeAddedNum = EmployeeDatabaseManipulator.addEmployee(employeeID, firstName, lastName, phoneNumber, email );

				if(employeeAddedNum == 1){
					idField.setText("");
					firstNameField.setText("");
					lastNameField.setText("");
					phoneNumberField.setText("");
					emailField.setText("");
				}

				// Updates the table displayed
				customTableModel.tableData = EmployeeDatabaseManipulator.getAllEmployeeData();
				customTableModel.fireTableDataChanged(); 
			}
		});

	}

	public void setUpRemoveEmployeePanel(){

		JLabel employeeIDLabel = new JLabel();
		employeeIDLabel.setText("<html>" + "Enter I.D. of Employee<br>you want to delete:" + "</html>");
		JButton removeEmployeeButton = new JButton("Remove Employee");
		JTextField idToRemoveField = new JTextField();

		removeEmployeePanel.add(employeeIDLabel);
		removeEmployeePanel.add(idToRemoveField);
		removeEmployeePanel.add(removeEmployeeButton);


	}

	public void setUpCalendarPanel(){
		
		String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		
		// Table Components / Table Models
		DefaultTableModel calTableModel = new DefaultTableModel();
		JTable calTable = new JTable(calTableModel);
		JScrollPane calScrollPane = new JScrollPane(calTable);
		
		// Table Controls
		JButton backButton = new JButton("<<");
		JButton forwardButton = new JButton(">>");
		JLabel yearLabel = new JLabel("Year");
		JLabel monthLabel = new JLabel("January");
		final JComboBox<Integer> yearComboBox = new JComboBox<Integer>();
		
		//Get Current Date
		GregorianCalendar gregCal = new GregorianCalendar();
		int currentMonth = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		int currentDay = gregCal.get(GregorianCalendar.MONTH);
		final int currentYear = gregCal.get(GregorianCalendar.YEAR);
		
		
		//Set Table Options
		calTable.setGridColor(Color.gray);
		
	
		// Set up columns in table
		for(int i = 0; i < daysOfTheWeek.length; i++){
			calTableModel.addColumn(daysOfTheWeek[i]);
		}
		
		// Row & Col Count
		calTableModel.setColumnCount(7);
		calTableModel.setRowCount(6);
		calTable.setRowHeight(66);
		
		// Set-up JComboBox
		for(int comboYear = currentYear - 100; comboYear <= currentYear + 100; comboYear++){
			yearComboBox.addItem(comboYear);
		}
		yearComboBox.setSelectedIndex(100); // Sets initial value to the current year
		
		calendarPanel.add(backButton);
		calendarPanel.add(monthLabel);
		calendarPanel.add(forwardButton);
		calendarPanel.add(calScrollPane);
		calendarPanel.add(yearLabel);
		calendarPanel.add(yearComboBox);
		refreshCalendar(calTable);
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
	}
	
	public void refreshCalendar(JTable table){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		table.repaint();
		table.revalidate();
	}


	// Custom Model on how a Table is displayed (Allows a Table to be built)
	private class MyTableModel extends AbstractTableModel{

		String[] columnNames ={
				"Employee ID",
				"First Name",
				"Last Name",
				"Phone Number",
				"Email",
				"Schedule"
		};

		Object[][] tableData = EmployeeDatabaseManipulator.getAllEmployeeData();

		@Override
		public int getRowCount() {
			return tableData.length;
		}

		@Override
		public int getColumnCount() {
			return tableData[0].length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return tableData[rowIndex][columnIndex];
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}
	}


}
