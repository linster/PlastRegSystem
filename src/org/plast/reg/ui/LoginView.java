package org.plast.reg.ui;

import java.io.File;

import javax.servlet.ServletContext;

import com.google.common.eventbus.EventBus;


import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import org.plast.reg.MasterNavigator;
import org.plast.reg.VaadinRequestHolder;
import org.springframework.*;
import org.springframework.security.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LoginView extends Panel implements View {

	

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to the login form");

	}
	
	public LoginView() {
		initlayout();
	}
	TextField tfusername = null;
	PasswordField tfpassword = null;
	Button blogin = null;
	
	private void initlayout() {
		
		

		//New FormLayout to hold Username / Password fields
		FormLayout flogin = new FormLayout();
		
		
		//Need to create a layout for our Logo to live in.
		HorizontalLayout logolayout = new HorizontalLayout();
		logolayout.setHeight("100px");
		FileResource rlogoimage = new FileResource( new File((VaadinService.getCurrent().getBaseDirectory().getAbsolutePath())+"/WEB-INF/images/logo.png" ));
		Image logoimage = new Image("", rlogoimage);
		logoimage.setHeight("80px");
		logolayout.addComponent(logoimage);
		Label main = new Label("<h1>PlastOnline</h1>",Label.CONTENT_XHTML);
		logolayout.addComponent(main);
		logolayout.setComponentAlignment(main, Alignment.BOTTOM_CENTER );
		
		flogin.addComponent(logolayout);
		
		tfusername = new TextField("Username");
		tfpassword = new PasswordField("Password");
			//Set fields as requited.
			tfusername.setRequiredError("Username cannot be blank");
			tfpassword.setRequiredError("Password cannot be blank");
		
		flogin.addComponent(tfusername);
		flogin.addComponent(tfpassword);
		
		blogin = new Button("Login");
		flogin.addComponent(blogin);
		
		//Make a Vertical Layout to house the form layout
		//So that we can center/center align the formlayout in the page
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(flogin);
		vl.setComponentAlignment(flogin, Alignment.TOP_RIGHT);
		setContent(vl);
		
		
		//BAD BAD BAD CODE
		//Should be refactored into the controller for this.
		blogin.addClickListener(new Button.ClickListener() { 
			public void buttonClick(ClickEvent event) {
				// Get the navigator object to work with later
				Navigator nav = MasterNavigator.getInstance().getNav();
				
				
				/*The following code is copy/pasted from this tutorial:
				 * http://packtlib.packtpub.com/library/9781782167525/ch06lvl1sec52
				 * 
				 * Had to add spring-context in manually from MavenDB since Ivy wouldn't pull it for some reason
				 * 
				 */
				
				/*
				 * Interesting (possibly relevant Stack Overflow page to rewrite the code below
				 * http://stackoverflow.com/questions/14373682/spring-security-vaadin-authetication-manager-bean
				 */
				try{ // Debug the AuthManager... getBean fails
					
					
			          ServletContext servletContext = VaadinRequestHolder.getRequest().getSession().getServletContext();
			          UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(tfusername.getValue(),tfpassword.getValue());
			            token.setDetails( new WebAuthenticationDetails(VaadinRequestHolder.getRequest()));
			            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			            AuthenticationManager authManager = wac.getBean(AuthenticationManager.class);
			            //AuthenticationManager authManager = (AuthenticationManager) wac.getBean("authenticationManager");
			            Authentication authentication = authManager.authenticate(token);
			            SecurityContextHolder.getContext().setAuthentication(authentication);
			            if(authentication.isAuthenticated()){
			              Notification.show("You are authenticated");
			              nav.navigateTo("Main");
			            	}


			    } catch (BadCredentialsException e) {

			      Notification.show("Bad credentials");
			    }
				//catch(NoSuchBeanDefinitionException b) {
			    //	Notification.show("Caught no bean exception");
			    //}
			    
				/*
				 * End of Spring Security Magic
				 */
			}
		} );

	}

}
