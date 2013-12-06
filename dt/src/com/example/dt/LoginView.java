package com.example.dt;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.*;

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
		setSizeFull();
		//New FormLayout to hold Username / Password fields
		FormLayout flogin = new FormLayout();
		setContent(flogin);
		
		tfusername = new TextField("Username");
		tfpassword = new PasswordField("Password");
			//Set fields as requited.
			tfusername.setRequiredError("Username cannot be blank");
			tfpassword.setRequiredError("Password cannot be blank");
		
		flogin.addComponent(tfusername);
		flogin.addComponent(tfpassword);
		
		blogin = new Button("Login");
		flogin.addComponent(blogin);
		
	}

}
