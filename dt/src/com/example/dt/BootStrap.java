package com.example.dt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class BootStrap extends UI {
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = BootStrap.class)
	public static class Servlet extends VaadinServlet {
		
		  @Override
		  protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		  SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
		  VaadinRequestHolder.setRequest(request);
		  super.service(request, response);
		  VaadinRequestHolder.clean();
		  SecurityContextHolder.clearContext();
		  }
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
	
	public void initBootStrap() {
		
		//Create the MasterNavigator and initialize it
		MasterNavigator.InstatiateNavigator(this, this);
		Navigator nav = MasterNavigator.getInstance().getNav();
		getPage().setTitle("Bootstrap Page");
		
		//Initialize the MainShell view and controller
		MainShellView msv = new MainShellView();
		MainShellControl msc = new MainShellControl(msv);
		//Register the MainShellView to the MasterNavigator
		nav.addView("Main", msv);
		
		//Initialize the Login view and controller
		LoginView lv = new LoginView();
		LoginController lc = new LoginController(lv);
		nav.addView("", lv);
		
		//Navigate to the MainShellView
		//nav.navigateTo("");
	}
	
	@Override
	protected void init(VaadinRequest request) {
	//No idea what the initialization sequence is for Vaadin. Hopefully
	// the constructor is run and does stuff before this method does anything.
		initBootStrap();
	}
}
