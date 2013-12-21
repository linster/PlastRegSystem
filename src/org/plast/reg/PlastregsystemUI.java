package org.plast.reg;

import org.plast.reg.LoginController;
import org.plast.reg.MainShellControl;
import org.plast.reg.ui.LoginView;
import org.plast.reg.ui.MainShellView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("plastregsystem")
public class PlastregsystemUI extends UI {
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
	@Override
	protected void init(VaadinRequest request) {
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
	}

}