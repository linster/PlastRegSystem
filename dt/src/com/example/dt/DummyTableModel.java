package com.example.dt;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.*;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DummyTableModel {
/** The DummyTableModel class allows seperation of concerns between the DummyTable (which is a View), and access to the /MODEL/, aka the database.
 * 	Each View class should have one of these partner classes nearby that provides access to the DB (loosly coupled compared to interfaces and
 *  extensions).
 * 
 */
	
	private DBhelper dbh = null;
	
	public DummyTableModel(){
		//Initialize the DBhelper object, get a connection pool.
		this.dbh = new DBhelper();
	}
	
	public SQLContainer GetTableQuerySQLContainer(String table){
	
		try{
			 TableQuery q1 = new TableQuery(table, dbh.getCpool());
		     q1.setVersionColumn("VERSION");
		     return new SQLContainer(q1);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
		
	}
	
	
}
