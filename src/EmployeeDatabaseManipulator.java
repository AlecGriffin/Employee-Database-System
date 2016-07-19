
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDatabaseManipulator {
	
	public Connection conn2 = establishConnection();

	public static Connection establishConnection(){
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "java_test";
		String driver = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "root";
		Connection conn = null;
		
		try{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, username, password);
		}catch(Exception e){
			e.printStackTrace();
		}

		// Connection(conn)  will never be null since an exception will be thrown if connection fails
		return conn;
	}
	
	// pre:
	//post: Adds an 'Employee' record to a database
	// Returns 1 if employee was added, false otherwise [ -1 means error occured]
	public static int addEmployee(int id, String firstName, String lastName, String phoneNumber, String email ){
			Connection conn = establishConnection();
		
		String query = "INSERT INTO Employees (EmployeeID, First_Name,Last_Name,Phone_Number,Email) "
					 + "VALUES ('"+ id + "','" + firstName + "','" + lastName + "','" + phoneNumber + "','" + email + "')";
		try {
			Statement st = conn.createStatement();
			if(employeeInDatabase(conn, id)){
				System.out.println("Employee " + firstName + " " + lastName + " is already present in the database!");
				return 0;
			}else{
				st.executeUpdate(query);
				System.out.println("Employee " + firstName + " " + lastName + "was successfully addded!");
				return 1;
			}
			
					
		} catch (SQLException e) {
			System.out.println("Employee Could Not Be Added!");
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
	
	
	//post: Removes an 'Employee' record from a database
	public static void removeEmployee(Connection conn, int ID, String firstName ){
		String query = "DELETE FROM Employees WHERE EmployeeID = '" + ID + "' AND First_Name = '" + firstName + "';";
		
		try{
			Statement st = conn.createStatement();
			st.executeUpdate(query);
			conn.close();
			
		}catch(SQLException e){
			System.out.println("Employee Could Not Be Removed!");
			e.printStackTrace();
		}
		System.out.println("Employee " + firstName +  " with an Employee ID of '" + ID +"' was successfully removed!");
	}
	
	//pre:
	//post: Returns true if Employee with id: 'id' is already present within the database
	public static boolean employeeInDatabase(Connection conn, int id){
		boolean isPresent = false;
		String query = "SELECT EmployeeID "
				+ "FROM Employees "
				+ "WHERE EmployeeID = " + id + " "
				+ "LIMIT 1";
		
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(query);
			if(res.next()){
				isPresent = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return isPresent;
	}

	//Post: Returns a 2D array of objects containing data for each employee 
	//Use: Used to populate table with employee information from a database
	public static Object[][] getAllEmployeeData(){
		Connection conn = establishConnection();
		
		String query = "SELECT * "
					+  "FROM Employees;";
		
		String queryToGetTotalNumRows = "SELECT COUNT(*) AS NumberOfEmployees FROM Employees";
		
		try {
			Statement st = conn.createStatement();
			ResultSet setContainingNumRows = st.executeQuery(queryToGetTotalNumRows);
			setContainingNumRows.next();
			int numRows = setContainingNumRows.getInt(1);
			
			Object[][] result = new Object[numRows][5];
			
			Statement st2 = conn.createStatement();
			ResultSet res = st2.executeQuery(query);
			
			while(res.next()){
				result[res.getRow() - 1][0] = res.getInt(1);
				result[res.getRow() - 1][1] = res.getString(2);
				result[res.getRow() - 1][2] = res.getString(3);
				result[res.getRow() - 1][3] = res.getString(4);
				result[res.getRow() - 1][4] = res.getString(5);
				
			}
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public ArrayList<String> getAllEmployeesWorking(int month, int day, int year) {
//		Connection conn = establishConnection();
		ArrayList<String> result = new ArrayList<String>();
		
		String query = "SELECT Name,Start_Time,End_Time "
				     + "FROM Employee_Work_Times "
					 + "WHERE Date=\'" + year + "-" + month + "-" + day + "\'";

		try {
			Statement st = conn2.createStatement();
			ResultSet setContainingWorkingEmployees = st.executeQuery(query);
			
			String  name, startTime, endTime;
			while(setContainingWorkingEmployees.next()){
				name = setContainingWorkingEmployees.getString(1);
				startTime = setContainingWorkingEmployees.getString(2);
				endTime= setContainingWorkingEmployees.getString(3);
				
				result.add(name + " " + startTime + "-" + endTime +  " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
