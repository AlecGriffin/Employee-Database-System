import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class Login_Verification {
	private static Connection establishConnection(){
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

	public static boolean verifyLoginCredentials(String username, String password){
		boolean credentialsCorrect = false;
		Connection conn = establishConnection();
		PreparedStatement checkLoginCredentials = null;
				
		String query = "SELECT * "
					+  "FROM users "
					+  "WHERE username = ? " 
					+  "AND password = ?";
	
		try {
			checkLoginCredentials = conn.prepareStatement(query);
			checkLoginCredentials.setString(1, username);
			checkLoginCredentials.setString(2, password);
			ResultSet res = checkLoginCredentials.executeQuery();
			
			if(res.next()){
				credentialsCorrect = true;
				System.out.println("LOGIN SUCCESSFUL");
			}else{
				System.out.println("LOGIN FAILED");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				checkLoginCredentials.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return credentialsCorrect; 
	}
}
