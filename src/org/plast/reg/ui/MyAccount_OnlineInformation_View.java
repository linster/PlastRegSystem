package org.plast.reg.ui;

import org.plast.reg.AuthenticationHolder;
import org.plast.reg.controller.BasicFormController;
import org.plast.reg.util.NoAuthenticationException;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

public class MyAccount_OnlineInformation_View extends PlastView implements View {

@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	try {
		buildLayout();
	} catch (NoAuthenticationException e) {
		Notification.show("NoAuth Exception when entering view");
	}
}

public MyAccount_OnlineInformation_View(BasicFormController b) {
	this.RegisterController(b);
	try {
		buildLayout();
	} catch (NoAuthenticationException e) {
		
	}
}

public void buildLayout() throws NoAuthenticationException{
	VerticalLayout layout = new VerticalLayout();
	Label heading = new Label ("<h1>User Account Information</h1>", Label.CONTENT_XHTML.HTML);
	layout.addComponent(heading);
	
	/* Make a form layout */
	FormLayout fl = new FormLayout();
	
	String username = AuthenticationHolder.getAuthentication().getName();
	//String username = "bob";
	
	TextField tfusername = new TextField("Username:", username);
	tfusername.setReadOnly(true);
	TextField tfcontactemail = new TextField("Contact Email Address:");
	Label newpass = new Label("If you do not want to change your password, leave "
			+ "the fields below blank");
	TextField tfnewpass = new TextField("Type a new password:");
	TextField tfconfnewpass = new TextField("Confirm new password:");
	Button bsubmit = new Button("Submit");
	
	fl.addComponent(tfusername);
	fl.addComponent(tfcontactemail);
	fl.addComponent(newpass);
	fl.addComponent(tfnewpass);
	fl.addComponent(tfconfnewpass);
	fl.addComponent(bsubmit);
	
	
	layout.addComponent(fl);
	setContent(layout);
}

}

