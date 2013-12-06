package com.example.dt;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

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
		blogin.addClickListener(new Button.ClickListener() { 
			public void buttonClick(ClickEvent event) {
				Notification.show("Button clicked");
				MasterNavigator.getInstance().getNav().navigateTo("Main");
			}
		} );

	}

}
