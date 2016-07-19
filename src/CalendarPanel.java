import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.security.auth.Refreshable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class CalendarPanel extends JPanel {
	
	JButton backButton;
	JButton forwardButton;
	JLabel yearLabel;
	JLabel monthLabel;
	JComboBox<Integer> yearComboBox;
	DefaultTableModel calTableModel;
	JTable calTable;
	JScrollPane calScrollPane;
	int currentDay, currentYear, currentMonth, realMonth, realYear, realDay;
	int numDays, startOfMonth;
	
	JPanel topCalPanel, bottomCalPanel;

	
	public CalendarPanel(){
		String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		backButton = new JButton("<<");
		forwardButton = new JButton(">>");
		yearLabel = new JLabel("Year");
		monthLabel = new JLabel("January");
		yearComboBox = new JComboBox<Integer>();
		calTableModel = new DefaultTableModel();
		calTable = new JTable(calTableModel);
		calScrollPane = new JScrollPane(calTable);
		
		topCalPanel = new JPanel();
		topCalPanel.setLayout(new BoxLayout(topCalPanel, BoxLayout.LINE_AXIS));
		bottomCalPanel = new JPanel();
		bottomCalPanel.setLayout(new BoxLayout(bottomCalPanel, BoxLayout.LINE_AXIS));
		
		
		// Acquires Current Date
		GregorianCalendar gregCal = new GregorianCalendar();
		currentDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		currentMonth = gregCal.get(GregorianCalendar.MONTH);
		currentYear = gregCal.get(GregorianCalendar.YEAR);
		realDay = currentDay;
		realMonth = currentMonth;
		realYear = currentYear;
		
		// Add Components to CalendarPanel
		topCalPanel.add(backButton);
		topCalPanel.add(monthLabel);
		topCalPanel.add(forwardButton);
		this.add(topCalPanel);
		this.add(calScrollPane);
		bottomCalPanel.add(yearLabel);
		bottomCalPanel.add(yearComboBox);
		this.add(bottomCalPanel);
		
		// Add Columns to Table
		for(int i = 0; i < daysOfTheWeek.length; i++){
			calTableModel.addColumn(daysOfTheWeek[i]);
		}
		
		//Set Table Details
		calTable.setGridColor(Color.gray);
		calTableModel.setColumnCount(7);
		calTableModel.setRowCount(6);
		calTable.setRowHeight(92);
		calTable.setDefaultRenderer(calTable.getColumnClass(0), new customTableRenderer());
		
		
		// Set-up JComboBox
		for(int comboYear = currentYear - 100; comboYear <= currentYear + 100; comboYear++){
			yearComboBox.addItem(comboYear);
		}
		yearComboBox.setSelectedIndex(100); // Sets initial value to the current year
		
		
		// Action Listeners (Using Anonymous Classes)
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentMonth == 0){
					currentMonth = 11;
					currentYear--;
				}else{
					currentMonth--;
				}
				
				refreshCalendar(currentYear, currentMonth);
			}
		});
		
		forwardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentMonth == 11){
					currentMonth = 0;
					currentYear++;
				}else{
					currentMonth++;
				}
				
				refreshCalendar(currentYear, currentMonth);
			}
		});
		
		
		
		refreshCalendar(currentYear, currentMonth);
	}
	
	public void refreshCalendar(int year, int month){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
		// Sets Month to be displayed
		monthLabel.setText(months[month]);
		
		//Clears Calendar
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				calTable.setValueAt(null, i, j);
			}
		}
		
		//Get Current Date
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		EmployeeDatabaseManipulator edm = new EmployeeDatabaseManipulator();
		for(int i = 1; i <= numDays; i++){
			
			// Gather Work Time From Database 
			// NOTE: [currentMonth + 1] was used because Months start at 0 --- ex.) Januray == 0
			
			
			ArrayList<String> workTimesList = edm.getAllEmployeesWorking(currentMonth + 1, i, currentYear);
			
			String workTimes = "";
			for(int j = 0; j < workTimesList.size(); j++){
				workTimes += (workTimesList.get(j) + "\n");
			}
			
			int row = (i + startOfMonth - 2) / 7;
			int col = (i + startOfMonth - 2) % 7;
			calTable.setValueAt(i + "\n" + workTimes, row, col);
		}
		
		yearComboBox.setSelectedItem(year);
		
		calTable.setDefaultRenderer(calTable.getColumnClass(0), new customTableRenderer());
		this.repaint();
		this.revalidate();
	}
	
	class customTableRenderer extends JTextArea implements TableCellRenderer{
		public customTableRenderer(){
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
			
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

				this.setText((value == null) ? "" : value.toString() + "\n");

// FOR FUTURE UPDATE ( For Highlighting box with current Date)
//				if(value != null)
//					System.out.println("Row: " + row + " Col: " + column + " Value: " + value.toString() );
//
//					if(value != null){
//						if(Integer.parseInt(value.toString()) ==  realDay - 1 && currentMonth == realMonth && currentYear == realYear){
//							this.setBackground(Color.red);
//							
//						}else{
//							this.setBackground(Color.white);
//						}
//					}
					
			return this;
		}
		
	}

}
