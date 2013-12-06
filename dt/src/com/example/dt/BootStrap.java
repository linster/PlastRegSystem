package com.example.dt;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class BootStrap extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MainShellView.class)
	public static class Servlet extends VaadinServlet {
	}
	
/** This is the main entry point of the Vaadin application.
 *  
 *  The purpose here is to instantiate the:
 *  	MasterNavigator singleton
 *  	All Views and Controllers
 *  then register:
 *  	All Views to the MasterNavigator
 *  then:
 *  	Navigate to the LoginView.
 */
	
	public BootStrap() {
		
		//Create the MasterNavigator and initialize it
		Navigator nav = MasterNavigator.getInstance().getNav();
		nav = new Navigator(this, this);
		
		//Initialize the MainShell view and controller
		MainShellView msv = new MainShellView();
		MainShellControl msc = new MainShellControl(msv);
		//Register the MainShellView to the MasterNavigator
		nav.addView("MainShellView", msv);
		
		//Navigate to the MainShellView
		nav.navigateTo("MainShellView");
	}

	@Override
	protected void init(VaadinRequest request) {
	//No idea what the initialization sequence is for Vaadin. Hopefully
	// the constructor is run and does stuff before this method does anything.
		
	}
}
