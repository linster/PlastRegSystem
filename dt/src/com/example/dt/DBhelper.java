package com.example.dt;

import java.sql.*;
import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

public class DBhelper {
	/** This class is responsible for setting up the database connection 
	 * 
	 */
	//If you're messing around in LibreOffice base, the driver string is org.openoffice.comp.drivers.MySQL.Driver
    private JDBCConnectionPool connectionPool = null;
	private static String url = "jdbc:mysql://mysql2.000webhost.com/a5980192_vaadin1";
	public static String username = "a5980192_vaadin";
	public static String password = "vaadin1";

	public DBhelper() {
		initConnectionPool();
    }

    private void initConnectionPool() {
      	try {
      		
          	connectionPool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver", DBhelper.getUrl(), DBhelper.username, DBhelper.password);
      	} catch (SQLException e) {
        	  e.printStackTrace();
        }
    }

	private static String getUrl() {
		return url;
	}
	
	public JDBCConnectionPool getCpool(){
		return connectionPool;
	}
	
	
	
	
	
}
