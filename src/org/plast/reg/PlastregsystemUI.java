package org.plast.reg;

import org.plast.reg.LoginController;
import org.plast.reg.MainShellControl;
import org.plast.reg.ui.LoginView;
import org.plast.reg.ui.MainShellView;
import org.plast.reg.events.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.vaadin.ui.Notification.TYPE_ERROR_MESSAGE;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
	
	private boolean canRegisterMVC = false; //This variable holds whether MVCs can be registered. Set to true after login.
	private EventBus authenticationBus = new EventBus();
	
	
	@Override
	protected void init(VaadinRequest request) {
		//Create the MasterNavigator and initialize it
		MasterNavigator.InstatiateNavigator(this, this);
		Navigator nav = MasterNavigator.getInstance().getNav();
		getPage().setTitle("Bootstrap Page");
				
		//Initialize the Login view and controller
		LoginView lv = new LoginView(authenticationBus);
		LoginController lc = new LoginController(lv);
		nav.addView("", lv);
		
	
			this.initMVC();
		
		
		//Register this class to the Authentication EventBus. (Some sort of magic 
		//happens with the @Subscribe annotation that allows binding to the eventbus
		authenticationBus.register(this);
		
		//Create a ViewChangeListener that respects the current Authentication state.
		//This prevents people from just typing in stuff into the URL bar and getting 
		//views that should be authenticated
		nav.addViewChangeListener(new ViewChangeListener() {
	        @Override
	        public boolean beforeViewChange(ViewChangeEvent event) {
	                if (event.getNewView() instanceof LoginView) {
	                	return true;
	                }

	                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	                return authentication == null ? false : authentication.isAuthenticated();
	        }
	        @Override
	        public void afterViewChange(ViewChangeEvent event) {}
	});
		
	}
	
	
	/**
	 * Initializes all Models, Views, Controllers. Called in PlastregsystemUI.login() after a sucessful login event.
	 */
	protected void initMVC(){
		Navigator nav = MasterNavigator.getInstance().getNav();		
		//Initialize the MainShell view and controller
		MainShellView msv = new MainShellView(authenticationBus);
		MainShellControl msc = new MainShellControl(msv);
		//Register the MainShellView to the MasterNavigator
		nav.addView("Main", msv);
	}

    @SuppressWarnings("deprecation")
	@Subscribe
    public void login(LoginEvent event) {

            AuthenticationService authHandler = new AuthenticationService();
    		Navigator nav = MasterNavigator.getInstance().getNav();
            try {
                    authHandler.handleAuthentication(event.getLogin(), event.getPassword(), VaadinRequestHolder.getRequest());
                    //this.canRegisterMVC = true;
                    //this.initMVC();
                    nav.navigateTo("Main");
            } catch (BadCredentialsException e) {
                    Notification.show("Invalid Username/Password. Please try again.",
                    		Notification.Type.WARNING_MESSAGE);
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