package com.example.dt;

import com.vaadin.navigator.Navigator;

public class MasterNavigator {
/** This is a singleton object that holds the master navigator for the application
 *  The BootStrap class (the first one run when the application is run) is responsible for
 *  registering all views into this MasterNavigator
 *  See the Google Doc: PlastRegSystem/Architectural Diagrams/M1 for details.
 */
	Navigator navigator;
	
	private static MasterNavigator instance = null;
	
	protected MasterNavigator() {
		//Null constructor to prevent instantiation
	}
	
	public static MasterNavigator getNav() { //Warning: this may not be thread-safe
		if (instance == null) {
			instance = new MasterNavigator();
		}
		return instance;
	}
}
