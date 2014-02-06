package org.plast.reg.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MainView extends Panel implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		Notification.show("Entered the MainView");
	}
	
	public MainView() {
		buildLayout();
	}
	
	public void buildLayout(){
		VerticalLayout layout = new VerticalLayout();
		Label label = new Label ("Thisis a cool label");
		layout.addComponent(label);
		setContent(layout);
	}

}
