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
import org.plast.reg.events.LoginEvent;
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

import com.google.common.eventbus.EventBus;
public class LoginView extends Panel implements View {

	

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to the login form");

	}
	
	public LoginView(final EventBus authEventBus) {
		initlayout(authEventBus);
	}
	TextField tfusername = null;
	PasswordField tfpassword = null;
	Button blogin = null;
	
	private void initlayout(final EventBus authEventBus) {
		
		

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
		
		//Allow login form to be centered
		GridLayout grid = new GridLayout(3,3);
		setContent(grid);
		grid.setColumnExpandRatio(0, 2);
		grid.setColumnExpandRatio(1, 1);
		grid.setColumnExpandRatio(2, 2);
		grid.setRowExpandRatio(0, 2);
		grid.setRowExpandRatio(1, 1);
		grid.setRowExpandRatio(2, 2);
		grid.setSizeFull();
		grid.addComponent(flogin, 1,1); //Add login to grid layout
		
		
		blogin.addClickListener(new Button.ClickListener() { 
			public void buttonClick(ClickEvent event) {

                LoginEvent loginEvent = new LoginEvent(tfusername.getValue(), tfpassword.getValue());
                authEventBus.post(loginEvent);
                tfusername.setValue("");
                tfpassword.setValue("");
			}
		} );

	}

}
