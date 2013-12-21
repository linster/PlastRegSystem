package org.plast.reg;

import org.plast.reg.LoginController;
import org.plast.reg.MainShellControl;
import org.plast.reg.ui.LoginView;
import org.plast.reg.ui.MainShellView;
import org.plast.reg.events.*;
import org.springframework.security.authentication.BadCredentialsException;

import static com.vaadin.ui.Notification.TYPE_ERROR_MESSAGE;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


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
	
	private EventBus authenticationBus = new EventBus();
	
	
	@Override
	protected void init(VaadinRequest request) {
		//Create the MasterNavigator and initialize it
		MasterNavigator.InstatiateNavigator(this, this);
		Navigator nav = MasterNavigator.getInstance().getNav();
		getPage().setTitle("Bootstrap Page");
		
		
		//Set the ViewChangeListener onto the MasterNavigator so that unauthorized access is denied.
		//nav.addViewChangeListener(new VaadinViewChangeCheck());
		
		//Initialize the MainShell view and controller
		MainShellView msv = new MainShellView(authenticationBus);
		MainShellControl msc = new MainShellControl(msv);
		//Register the MainShellView to the MasterNavigator
		nav.addView("Main", msv);
		
		//Initialize the Login view and controller
		LoginView lv = new LoginView(authenticationBus);
		LoginController lc = new LoginController(lv);
		nav.addView("", lv);
		
		//Register this class to the Authentication EventBus. (Some sort of magic 
		//happens with the @Subscribe annotation that allows binding to the eventbus
		authenticationBus.register(this);
	}

    @SuppressWarnings("deprecation")
	@Subscribe
    public void login(LoginEvent event) {

            AuthenticationService authHandler = new AuthenticationService();
    		Navigator nav = MasterNavigator.getInstance().getNav();

            try {
                    authHandler.handleAuthentication(event.getLogin(), event.getPassword(), VaadinRequestHolder.getRequest());
                    nav.navigateTo("Main");
            } catch (BadCredentialsException e) {
                    Notification.show("Bad credentials");
            }
    }

    @Subscribe
    public void logout(LogoutEvent event) {
			Navigator nav = MasterNavigator.getInstance().getNav();
            AuthenticationService authHandler = new AuthenticationService();
            authHandler.handleLogout(VaadinRequestHolder.getRequest());
            nav.navigateTo("");
    }
	
	
}