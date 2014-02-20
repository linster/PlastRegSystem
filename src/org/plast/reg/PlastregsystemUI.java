package org.plast.reg;

import org.plast.reg.LoginController;
import org.plast.reg.MainShellControl;
import org.plast.reg.ui.LoginView;
import org.plast.reg.ui.MainShellView;
import org.plast.reg.ui.MainView;
import org.plast.reg.ui.MyAccount_OnlineInformation_View;
import org.plast.reg.ui.MyAccount_PersonalInformation_View;
import org.plast.reg.util.ViewChangeSecurityChecker;
import org.plast.reg.events.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.vaadin.ui.Notification.TYPE_ERROR_MESSAGE;

import com.vaadin.annotations.PreserveOnRefresh;
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
//BUG: Welcome Jim is repeated one more time upon every login.
//Removing this annotation does mean that a refresh of the page dumps you to the login screen.
//@PreserveOnRefresh
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
	public Navigator nav;
	public MainShellView msv;
	
	@Override
	protected void init(VaadinRequest request) {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		
		//Initialize the MainShell view and controller
		msv = new MainShellView(authenticationBus, "Plast Online");
		setContent(msv);
		//Create the MasterNavigator and initialize it
		this.nav = new Navigator(PlastregsystemUI.this, this.msv.apppanel);
		this.setId("PlastRegSystemUI");

		getPage().setTitle("Bootstrap Page");
		
		
		//Initialize the Login view and controller
		LoginView lv = new LoginView(authenticationBus);
		LoginController lc = new LoginController(lv);
		nav.addView("Login", lv);
		
		
		
		//Register the MainShellView to the MasterNavigator
		MainView mv = new MainView();
		nav.addView("Main", mv);
		
		//Initialize the Online Information Manager View and Controller
		//TODO: Still using a dummy controller
		MyAccount_OnlineInformation_View mov = new MyAccount_OnlineInformation_View();
		nav.addView("My_Account__Online_Information", mov);
		
		MyAccount_PersonalInformation_View mpv = new MyAccount_PersonalInformation_View();
		nav.addView("My_Account__Personal_Information", mpv);

		
		//Register this class to the Authentication EventBus. (Some sort of magic 
		//happens with the @Subscribe annotation that allows binding to the eventbus
		authenticationBus.register(this);
		
		//Create a ViewChangeListener that respects the current Authentication state.
		//This prevents people from just typing in stuff into the URL bar and getting 
		//views that should be authenticated
		
		nav.addViewChangeListener(new ViewChangeSecurityChecker());
		//Navigate to the login view
		nav.navigateTo("Login");
		
	}
	
   
	@Subscribe
    public void login(LoginEvent event) {

            AuthenticationService authHandler = new AuthenticationService();
            Navigator nav = UI.getCurrent().getNavigator();
            try {
                    authHandler.handleAuthentication(event.getLogin(), event.getPassword(), VaadinRequestHolder.getRequest());
                    // Set the Authentication object into a Thread_Local so that the view change checker works.
                    AuthenticationHolder.setAuthentication(SecurityContextHolder.getContext().getAuthentication());
                    msv.setAuthentication(SecurityContextHolder.getContext().getAuthentication());
                    msv.rebuildView();
                    nav.navigateTo("Main");
                    //nav.navigateTo("My_Account__Online_Information");
            } catch (BadCredentialsException e) {
                    Notification.show("Invalid Username/Password. Please try again.",
                    		Notification.Type.WARNING_MESSAGE);
            }
    }

    @Subscribe
    public void logout(LogoutEvent event) {
			Navigator nav = UI.getCurrent().getNavigator();
			AuthenticationService authHandler = new AuthenticationService();
			//Clear the Thread_Local-stored Authentication object. (Stored so the view change listener checker works
			/** BUG:
			 * 	If you log out of the system, then type in the URL for a protected page again, the page will display. If you create a 
			 *  new tab (or a new browser session), page protection works properly. A user who was never authenticated still cannot view
			 *  a protected page, however. For some reason, if I clear the THREAD_LOCAL Authentication object in the Logout Handler, the 
			 *  ViewChangeSecurityChecker doesn't work.
			 */
			//AuthenticationHolder.clean();
            authHandler.handleLogout(VaadinRequestHolder.getRequest());
            msv.handleLogout();
            nav.navigateTo("Login");
    }
	
	
}