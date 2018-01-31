package com.utils.dbconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mySchema?useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String MAX_POOL = "200"; 
	
	private static DBConnection instace;
	private static Connection connection;
	private Properties properties;
	
	public DBConnection() {
		try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            System.out.println("\n=======Database Connection Open=======\n");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
        	ex.printStackTrace();
		} 
		
	}
	
	public static DBConnection getInstance() {
		
		if(instace == null) {

			synchronized (DBConnection.class) {
				
				if(instace == null) {
					instace = new DBConnection();							
				}
				
			}
		}

		return instace;
	}
	
	private Properties getProperties() {
	    if (properties == null) {
	        properties = new Properties();
	        properties.setProperty("user", USERNAME);
	        properties.setProperty("password", PASSWORD);
	        properties.setProperty("MaxPooledStatements", MAX_POOL);
	    }
	    return properties;
	}
	
	public void disconnect() {
	    if (connection != null) {
	        try {
	            connection.close();
	            connection = null;
	            System.out.println("\n=======Database Connection Closed=======\n");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public Integer createCountQuery(String query) {
		Integer count = null;
		try {
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			resultSet.next();
			count = resultSet.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}