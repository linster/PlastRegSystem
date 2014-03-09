package org.plast.reg.ui;

import org.plast.reg.AuthenticationHolder;
import org.plast.reg.util.NoAuthenticationException;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

public class MyAccount_PersonalInformation_View extends PlastView implements View {

@Override
public void enter(ViewChangeEvent event) {
	// TODO Auto-generated method stub
	try {
		buildLayout();
	} catch (NoAuthenticationException e) {
		Notification.show("NoAuth Exception when entering view");
	}
}

public MyAccount_PersonalInformation_View() {
	try {
		buildLayout();
	} catch (NoAuthenticationException e) {
		
	}
}

public void buildLayout() throws NoAuthenticationException{
	VerticalLayout layout = new VerticalLayout();
	Label heading = new Label ("<h1>Personal Information</h1>", Label.CONTENT_XHTML.HTML);
	layout.addComponent(heading);
	
	/* Make a form layout */
	FormLayout fl = new FormLayout();
	
	String username = AuthenticationHolder.getAuthentication().getName();
	
	
	TextField TFfirstname = new TextField("First name:");
	TextField TFlastname = new TextField("Last Name:");
	TextField TFmiddlename = new TextField("Middle Name:");
	//Date picker for birthdate
	DateField DFbirthdate = new DateField("Birth Date");
	//TODO: INPUT VALIDATOR
	Label lphone = new Label("Input a phone number in the form: (780)492-5555");
	TextField TFphone = new TextField("Phone Number:");
	TextField TFcellphone = new TextField("Cell Phone:");
	
	TextField tfcontactemail = new TextField("Contact Email Address:");
	
	Button bsubmit = new Button("Submit");
	
	fl.addComponent(TFfirstname);
	fl.addComponent(TFlastname);
	fl.addComponent(TFmiddlename);
	fl.addComponent(DFbirthdate);
	fl.addComponent(lphone);
	fl.addComponent(TFphone);
	fl.addComponent(TFcellphone);
	fl.addComponent(tfcontactemail);
	fl.addComponent(bsubmit);
	
	layout.addComponent(fl);
	setContent(layout);
}

}

