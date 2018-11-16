package application;

import java.sql.*;

public class DBClass {
	
	private String url = "jdbc:mysql://localhost:3306/Restaurant";
	private String user = "test";
	private String password = "test1234";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{       
        return DriverManager.getConnection(url,user,password); 
    }
	
}
